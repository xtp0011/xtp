package com.imooc.sell.dao;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.sell.dao.CategroyDao;
import com.imooc.sell.entity.ProductCategory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategroyDaoTest {
	@Autowired
	private CategroyDao dao ;
	
	@Test
	public void findOneTest() {
		 Optional<ProductCategory> productCategory = dao.findById(1);
		 System.out.println(productCategory.get());
	}
	
	@Test
	//@Transactional 在测试类完全回滚事务
	public void saveTest() {
		ProductCategory category = new ProductCategory();
		//category.setCategoryId(2);
		category.setCategoryName("女生最爱");
		category.setCategoryType(2);
		dao.save(category);
	}

	@Test
	public void findByCategoryTypeTest() {
		List<Integer> list = Arrays.asList(1,2,3);
		List<ProductCategory> typeList = dao.findByCategoryTypeIn(list);
		System.out.println(typeList);
	}
}
