package com.imooc.sell.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.sell.dto.OrderDto;
import com.imooc.sell.entity.OrderDetail;
import com.imooc.sell.service.impl.OrderServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
	@Autowired
	private OrderServiceImpl service;
	private static final String buyerOpenid = "1101110";
	private static final String orderid = "1547354511211536895";
	@Test
	public void create() {
		OrderDto  od = new OrderDto(); 
		od.setBuyerName("燎师兄");
		od.setBuyerAddress("慕课网");
		od.setBuyerPhone("12324241322142");
		od.setBuyerOpenid(buyerOpenid);
		List<OrderDetail> ods = new ArrayList<OrderDetail>();
		OrderDetail o = new OrderDetail();
		o.setProductId("123456");
		o.setProductQuantity(3);
		ods.add(o);
		od.setOrderDetailList(ods);
		OrderDto create = service.create(od);
		System.out.println(create);
	}
	@Test
	public void findByOrderId() {
		OrderDto dto = service.findByOrderId(orderid);
		System.out.println(dto);
	}

	@Test
	public void findList() {
		PageRequest pageRequest = new PageRequest(0, 3);
		Page<OrderDto> findList = service.findList(buyerOpenid, pageRequest);
		System.out.println(findList.getContent());
	}
	
	@Test
	public void cancel() {
		OrderDto dto = service.findByOrderId(orderid);
		OrderDto cancel = service.cancel(dto);
		System.out.println(cancel);
	}
}
