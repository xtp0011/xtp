package com.imooc.sell.dao;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.sell.entity.OrderMaster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterDaoTest {
	
	@Autowired
	private OrderMasterDao dao;
	
	@Test
	public void save() {
		OrderMaster om = new OrderMaster();
		om.setOrderId("1234567");
		om.setBuyerName("师兄");
		om.setBuyerPhone("13314351234");
		om.setBuyerAddress("慕课网");
		om.setBuyerOpenid("110110");
		om.setOrderAmount(new BigDecimal(2.5));
		dao.save(om);
	}
	
	@Test
	public void findByBuyerOpenid() {
		String openid= "110110";
		PageRequest request = new PageRequest(0,1);
		Page<OrderMaster> page = dao.findByBuyerOpenid(openid, request);
		System.out.println(page.getContent());
	}
}
