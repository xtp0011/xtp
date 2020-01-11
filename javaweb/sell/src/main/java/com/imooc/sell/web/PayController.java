package com.imooc.sell.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.OrderService;
import com.imooc.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
/**
 * 支付
 * @author 徐太平
 *
 */
@Controller
@RequestMapping("/pay")
public class PayController {
	@Autowired
	private OrderService orderSerive;
	
	@Autowired
	private PayService payService;
	
	@GetMapping("create")
	public ModelAndView create(@RequestParam("orderId")String orderId,
			@RequestParam("rediretUrl") String rediretUrl,
			Map<String,Object> map) {
		//查询订单
		OrderDto orderDto = orderSerive.findByOrderId(orderId);
		if(orderDto==null) {
			throw new SellException(ReslutEnum.ORDER_DETAIL_NOT_EXIST);
		}
		//发起支付
		PayResponse payResponse = payService.create(orderDto);
		map.put("payResponse", payResponse);
		map.put("returnUrl",rediretUrl);
		return new ModelAndView("pay/create",map);
	}
	
	@PostMapping("/notify")
	public ModelAndView notify(@RequestBody String notifyData) {
		payService.notify(notifyData);
		//返回给微信处理结果
		return new ModelAndView("pay/success");
	}
}
