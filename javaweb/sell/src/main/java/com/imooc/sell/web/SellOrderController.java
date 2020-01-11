package com.imooc.sell.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;

import lombok.extern.slf4j.Slf4j;

/**
 * 卖家端订单
 * @author 徐太平
 *
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellOrderController {
	@Autowired
	private OrderService orderService;
	/**
	 * 订单列表
	 * @param page 
	 * @param size
	 * @return
	 */
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value="page",defaultValue="1") Integer page,
			@RequestParam(value="size",defaultValue="10") Integer size,Map<String,Object> map) {
		PageRequest pageRequest = PageRequest.of(page-1, size);
		Page<OrderDto> list = orderService.findList(pageRequest);
		map.put("orderDtoPage", list);
		map.put("currentPage", page);
		map.put("size", size);
		return new ModelAndView("order/list",map);
	}
	
	/**
	 * 取消
	 * @param orderId
	 * @return
	 */
	@GetMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderId") String orderId,
			Map<String,Object> map) {
		try {
			OrderDto orderDto = orderService.findByOrderId(orderId);
			orderService.cancel(orderDto);
		} catch (SellException e) {
			log.error("【卖家端取消订单】 发生异常{}",e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error",map);
		}
		map.put("msg", ReslutEnum.ORDER_CANCEL_SUCCESS.getMessage());
		map.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success",map);
	}
	
	/**
	 * 订单详情
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("orderId") String orderId,
			Map<String,Object> map) {
		OrderDto orderDto = new OrderDto();
		try {
			 orderDto = orderService.findByOrderId(orderId);
		} catch (Exception e) {
			log.error("【卖家端查询订单详情】 发生异常{}",e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error",map);
		}
		map.put("orderDto", orderDto);
		return new ModelAndView("order/detail",map);
	}
	
	/**
	 * 完结订单
	 * @param orderId
	 * @param map
	 * @return
	 */
	@GetMapping("/finish")
	public ModelAndView finished(@RequestParam("orderId") String orderId,
			Map<String,Object> map) {
		try {
			OrderDto orderDto = orderService.findByOrderId(orderId);
			orderService.finish(orderDto);
		} catch (SellException e) {
			log.error("【卖家端完结订单】 发生异常{}",e);
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error",map);
		}
		map.put("msg", ReslutEnum.ORDER_FINISH_SUCCESS.getMessage());
		map.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success",map);
	}
}
