package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDto;

/**
 * 消息推送
 * @author 徐太平
 *
 */
public interface PushMessageService {
	/**
	 * 订单状态变更消息
	 * @param orderDto
	 */
	void orderStatus(OrderDto orderDto);
}
