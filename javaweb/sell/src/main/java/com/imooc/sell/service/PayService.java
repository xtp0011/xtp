package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDto;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
/**
 * 支付
 * @author 徐太平
 *
 */
public interface PayService {
	PayResponse create(OrderDto orderDto);
	PayResponse notify(String notifyData);
	RefundResponse refund(OrderDto orderDto);
	
}
