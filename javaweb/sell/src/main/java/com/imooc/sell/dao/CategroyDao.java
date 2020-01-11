package com.imooc.sell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.sell.entity.ProductCategory;

public interface CategroyDao extends JpaRepository<ProductCategory, Integer>{
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
	
}
