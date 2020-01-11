package com.imooc.sell.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.imooc.sell.VO.ResultVO;
import com.imooc.sell.converter.OrderFromToOrderDtoConverter;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.from.OrderFrom;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.util.ResultVOUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
	@Autowired
	private OrderService orderService ; 
	
	@Autowired
	private BuyerService buyerService;
	//创建订单
	@PostMapping("/create")
	public  ResultVO<Map<String,String>> create(@Valid OrderFrom orderFrom,BindingResult bindingResult){
		if(bindingResult.hasErrors()) {
			log.error("【创建订单】 参数不正确,orderFrom={}",orderFrom);
			throw new SellException(ReslutEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		
		OrderDto orderDto = OrderFromToOrderDtoConverter.convert(orderFrom);
		System.out.println(orderDto.getOrderDetailList());
		if(CollectionUtils.isEmpty(orderDto.getOrderDetailList())) {
			log.error("【创建订单】 购物车不能为空");
			throw new SellException(ReslutEnum.CHAT_EMPTY);
		}
		OrderDto createResult = orderService.create(orderDto);
		Map<String,String> map = new HashMap<String, String>();
		map.put("orderId", createResult.getOrderId());
		return ResultVOUtil.success(map);
		
	}
	//订单列表
	@GetMapping("/list")
	public ResultVO<List<OrderDto>> list(@RequestParam("openid") String openid,
			@RequestParam(value="page",defaultValue="0") Integer page,
			@RequestParam(value="size",defaultValue="10") Integer size){
		if(StringUtils.isEmpty(openid)) {
			log.error("【查询订单列表】 openid为空");
			throw new SellException(ReslutEnum.PARAM_ERROR);
		}
		PageRequest request =  PageRequest.of(page,size);
		Page<OrderDto> orderDtoPage = orderService.findList(openid, request);
		return ResultVOUtil.success(orderDtoPage.getContent()) ;
	}
	
	//订单详情
	@GetMapping("/detail")
	public ResultVO<OrderDto> detail(@RequestParam("openid") String openid,
			@RequestParam("orderId") String orderId){
		OrderDto orderDto = buyerService.cancelOrder(openid, orderId);
		return ResultVOUtil.success(orderDto);
	}
	//取消订单
	@PostMapping("/cancel")
	public ResultVO calcel(@RequestParam("openid") String openid,
			@RequestParam("orderId") String orderId) {
		buyerService.cancelOrder(openid, orderId);
		return ResultVOUtil.success();
	}

}
