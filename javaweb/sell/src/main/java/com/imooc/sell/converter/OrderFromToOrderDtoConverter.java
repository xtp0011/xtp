package com.imooc.sell.converter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.from.OrderFrom;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class OrderFromToOrderDtoConverter {
	public static OrderDto convert(OrderFrom orderFrom) {
		Gson gson = new Gson();
		OrderDto orderDto = new OrderDto();
		orderDto.setBuyerName(orderFrom.getName());
		orderDto.setBuyerPhone(orderFrom.getPhone());
		orderDto.setBuyerAddress(orderFrom.getAddress());
		orderDto.setBuyerOpenid(orderFrom.getOpenid());
		List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
		try {
			orderDetailList=gson.fromJson(orderFrom.getItems(),new TypeToken<List<OrderDetail>>() {
			}.getType());			
		} catch (Exception e) {
			log.error("【对象转换】 错误,String ={}",orderFrom.getItems());
			throw new SellException(ReslutEnum.PARAM_ERROR);
		}
		 orderDto.setOrderDetailList(orderDetailList);
		 return orderDto;
	}

}
