package com.imooc.sell.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.service.impl.CategroyServicImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServerImplTest {
	
	@Autowired 
	private CategroyServicImpl service;

	
	@Test
	public void findById() {
		ProductCategory categroy = service.findById(1);
		System.out.println(categroy);
	}

	@Test
	public void findAll() {
		List<ProductCategory> findAll = service.findAll();
		System.out.println(findAll);
	}

	@Test
	public void findByCategroyTypeIn() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		List<ProductCategory> findByCategroyTypeIn = service.findByCategroyTypeIn(list);
		System.out.println(findByCategroyTypeIn);
	}

	@Test
	public void save() {
		ProductCategory	productCategory = new ProductCategory();
		productCategory.setCategoryName("热销帮");
		productCategory.setCategoryType(1);
		service.save(productCategory);
	}
}
