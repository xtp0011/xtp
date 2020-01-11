package com.imooc.sell.service.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.imooc.sell.converter.OrdetMasterToOrderDtoConverter;
import com.imooc.sell.dao.OrderDetailDao;
import com.imooc.sell.dao.OrderMasterDao;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.entity.OrderMaster;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.service.PushMessageService;
import com.imooc.sell.service.WebSocket;
import com.imooc.sell.util.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductInfoService prductInfoService;
	
	@Autowired
	private OrderDetailDao orderDetailDao; 
	
	@Autowired
	private OrderMasterDao orderMasterDao;
	
	@Autowired
	private PayService payService;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private WebSocket webSocket;
	
	@Override
	@Transactional
	public OrderDto create(OrderDto orderDto) {
		String orderId = KeyUtil.genUniqueKey();
		BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
//		List<CartDto> cartDtoList = new ArrayList<CartDto>();
		//1.查询商品
		for (OrderDetail orderDetail : orderDto.getOrderDetailList()) {
			ProductInfo productInfo = prductInfoService.findByProductId(orderDetail.getProductId());
			if(productInfo==null) {
				throw new SellException(ReslutEnum.PRODUCT_NOT_EXIST);
			}
			//2.计算订单总结总价
			orderAmount =productInfo.getProductPrice()
					.multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
			
			//3.写入订单详情数据
			BeanUtils.copyProperties(productInfo, orderDetail);
			orderDetail.setDetailId(KeyUtil.genUniqueKey());
			orderDetail.setOrderId(orderId);
			orderDetailDao.save(orderDetail);
//			CartDto cartDto =new CartDto(orderDetail.getProductId(), orderDetail.getProductQuantity());
//			cartDtoList.add(cartDto);
		}
		//4.写入订单
		OrderMaster orderMaster = new OrderMaster();
		orderDto.setOrderId(orderId);
		BeanUtils.copyProperties(orderDto,orderMaster);
		orderMaster.setOrderAmount(orderAmount);
		orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
		orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
		orderMasterDao.save(orderMaster);
		//5.扣库存
		List<CartDto> cartDtoList = new ArrayList<CartDto>();
		cartDtoList=orderDto.getOrderDetailList().stream().map(e->new CartDto(e.getProductId(),e.getProductQuantity()))
		.collect(Collectors.toList());
		prductInfoService.decreaseStock(cartDtoList);
		webSocket.sendMessage("有新的订单");
		return orderDto;
	}

	@Override
	public OrderDto findByOrderId(String orderId) {
		OrderMaster orderMaster = orderMasterDao.findById(orderId).get();
		if(orderMaster==null) {
			throw new SellException(ReslutEnum.ORDER_NOT_EXIST);
		}
		List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderId);
		if(CollectionUtils.isEmpty(orderDetailList)) {
			throw new SellException(ReslutEnum.ORDER_DETAIL_NOT_EXIST);
		}
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(orderMaster, orderDto);
		orderDto.setOrderDetailList(orderDetailList);
		return orderDto;
	}

	@Override
	public Page<OrderDto> findList(String buyerOpenid, Pageable pageable) {
		Page<OrderMaster> orderMassterPage = orderMasterDao.findByBuyerOpenid(buyerOpenid, pageable);
		
		List<OrderDto> orderDtoList = OrdetMasterToOrderDtoConverter.convert(orderMassterPage.getContent());
		
		Page<OrderDto> orderPageDto = new PageImpl<OrderDto>(orderDtoList,pageable,orderMassterPage.getTotalElements());
		
		return orderPageDto;
	}

	@Override
	@Transactional
	public OrderDto cancel(OrderDto orderDto) {
		OrderMaster orderMaster = new OrderMaster();
		//判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】 订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ReslutEnum.ORDER_STATUS_ERROR);
		}
		//修改订单状态
		orderDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDto, orderMaster);
		OrderMaster updateResult = orderMasterDao.save(orderMaster);
		if(updateResult==null) {
			log.error("【取消订单】 更新失败，orderMaster={}",updateResult);
			throw new SellException(ReslutEnum.ORDER_UPDATE_FAIL);
		}
		//返还库存
		if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
			log.error("【取消订单】 订单中无商品，orderDto={}",orderDto);
			throw new SellException(ReslutEnum.ORDER_DETAIL_EMPTY);
		}
		List<CartDto> cartDtoList = orderDto.getOrderDetailList().stream()
				.map(e->new CartDto(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
		prductInfoService.increaseStock(cartDtoList);
		//如果已支付需退款
		if(orderDto.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
			payService.refund(orderDto);
		}
		return orderDto;
	}

	@Override
	@Transactional
	public OrderDto finish(OrderDto orderDto) {
		//判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【取消订单】 订单状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ReslutEnum.ORDER_STATUS_ERROR);
		}
		//修改状态
		orderDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDto, orderMaster);
		OrderMaster updateReslut = orderMasterDao.save(orderMaster);
		if(updateReslut==null) {
			log.error("【取消订单】 更新失败，orderMaster={}",updateReslut);
			throw new SellException(ReslutEnum.ORDER_UPDATE_FAIL);
		}
		//推送微信模板消息
		pushMessageService.orderStatus(orderDto);
		return orderDto;
	}

	@Override
	@Transactional
	public OrderDto paid(OrderDto orderDto) {
		//判断订单状态
		if(!orderDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
			log.error("【订单支付完成】 订单支付状态不正确，orderId={},orderStatus={}",orderDto.getOrderId(),orderDto.getOrderStatus());
			throw new SellException(ReslutEnum.ORDER_STATUS_ERROR);
		}
		//判断支付状态
		if(!orderDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
			log.error("【订单支付完成】 订单支付状态不正确，orderDto={}",orderDto);
			throw new SellException(ReslutEnum.ORDER_PAY_STATUS_ERROR);
		}
		//修改支付状态
		//修改状态
		orderDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
		OrderMaster orderMaster = new OrderMaster();
		BeanUtils.copyProperties(orderDto, orderMaster);
		OrderMaster updateReslut = orderMasterDao.save(orderMaster);
		if(updateReslut==null) {
			log.error("【订单支付完成】 更新失败，orderMaster={}",updateReslut);
			throw new SellException(ReslutEnum.ORDER_UPDATE_FAIL);
		}
		return orderDto;
	}

	@Override
	public Page<OrderDto> findList(Pageable pageable) {
		Page<OrderMaster> orderMassterPage = orderMasterDao.findAll(pageable);
		List<OrderDto> orderDtoList = OrdetMasterToOrderDtoConverter.convert(orderMassterPage.getContent());
		Page<OrderDto> orderPageDto = new PageImpl<OrderDto>(orderDtoList,pageable,orderMassterPage.getTotalElements());
		return orderPageDto;
	}

}
