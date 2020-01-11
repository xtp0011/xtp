package com.imooc.sell.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.service.impl.ProductInfoServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {
	@Autowired
	private ProductInfoServiceImpl service;
	
	@Test
	public void findByProductId() {
		ProductInfo byId = service.findByProductId("123456");
		System.out.println(byId);
	}

	@Test
	public void findUpAll() {
		List<ProductInfo> findAll = service.findUpAll();
		System.out.println(findAll);
	}

	@Test
	public void findAll(){
		PageRequest  pageable = new PageRequest(0, 2);
		Page<ProductInfo> findAll = service.findAll(pageable);
		System.out.println(findAll.getContent());
	}

	@Test
	public void save() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setProductId("223456");
		productInfo.setProductName("酸辣土豆丝");
		productInfo.setProductPrice(new BigDecimal(15));
		productInfo.setProductStock(100);
		productInfo.setProductIcon("http://xxxx.jpg");
		productInfo.setProductDescription("非常好吃");
		productInfo.setProductStatus(0);
		productInfo.setCategoryType(1);
		service.save(productInfo);
	}

}
