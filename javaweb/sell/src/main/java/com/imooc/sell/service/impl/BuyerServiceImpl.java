package com.imooc.sell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

	@Autowired
	private OrderService orderService;
	@Override
	public OrderDto findOrderOne(String openid, String orderId) {
		return checkOrderOwner(openid, orderId);
	}

	@Override
	public OrderDto cancelOrder(String openid, String orderId) {
		OrderDto orderDto = checkOrderOwner(openid, orderId);
		if(orderDto==null) {
			log.error("【取消订单】 查不到该订单 ,orderId={}",orderId);
			throw new SellException(ReslutEnum.ORDER_NOT_EXIST);
		}
		return orderService.cancel(orderDto);
	}
	
	private OrderDto checkOrderOwner(String openid, String orderId) {
		OrderDto orderDto = orderService.findByOrderId(orderId);
		if(orderDto==null) {
			return null;
		}
		if(!orderDto.getBuyerOpenid().equals(openid)) {
			log.error("【查询订单】 订单的openid不一致,openid={},orderId={}",openid,orderId);
			throw new SellException(ReslutEnum.ORDER_OWNER_ERROE);
		}
		return orderDto;
	}
}
