package com.imooc.sell.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.sell.entity.OrderDetail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailDaoTest {
	@Autowired
	private OrderDetailDao dao ;  
	
	@Test
	public void save() {
		OrderDetail od = new OrderDetail() ; 
		od.setDetailId("13243523");
		od.setOrderId("12345213267");
		od.setProductIcon("http://xxxx.log");
		od.setProductId("121324333215");
		od.setProductName("麻婆豆腐");
		od.setProductPrice(new BigDecimal(11.7));
		od.setProductQuantity(3);
		dao.save(od);
	}
	
	@Test
	public void findByOrderId() {
		List<OrderDetail> list = dao.findByOrderId("1234567");
		System.out.println(list);
	}

}
