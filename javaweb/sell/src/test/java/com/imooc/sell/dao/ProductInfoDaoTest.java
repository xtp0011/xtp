package com.imooc.sell.dao;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.sell.entity.ProductInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoDaoTest {
	@Autowired
	private ProductInfoDao dao;
	
	@Test
	public void saveTest() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("123456");
		productInfo.setProductName("皮蛋粥");
		productInfo.setProductPrice(new BigDecimal(3.2));
		productInfo.setProductStock(100);
		productInfo.setProductIcon("http://xxxx.jpg");
		productInfo.setProductDescription("很好喝");
		productInfo.setProductStatus(0);
		productInfo.setCategoryType(1);
		dao.save(productInfo);
	}

	@Test
	public void findByProductStatus() {
		List<ProductInfo> list = dao.findByProductStatus(0);
		System.out.println(list);
	}
}
