package com.imooc.sell.service;

import java.util.List;

import com.imooc.sell.entity.ProductCategory;

/**
 * 商品类目
 * @author 徐太平
 *
 */
public interface CategroyService {
	ProductCategory findById(Integer categroyId);
	
	List<ProductCategory> findAll();
	
	List<ProductCategory> findByCategroyTypeIn(List<Integer> categroyTypeList);
	
	ProductCategory save(ProductCategory productCategory);

}
