package com.imooc.sell.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.entity.OrderMaster;

public class OrdetMasterToOrderDtoConverter {
	public static OrderDto convert(OrderMaster orderMaster) {
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(orderMaster, orderDto);
		return orderDto;
	}
	
	public static List<OrderDto> convert(List<OrderMaster> orderMasterList) {
		List<OrderDto> orderDtoList = orderMasterList.stream().map(e->convert(e)).collect(Collectors.toList());
		return orderDtoList;
	}

}
