package com.imooc.sell.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.sell.dao.CategroyDao;
import com.imooc.sell.entity.ProductCategory;
import com.imooc.sell.service.CategroyService;

@Service
public class CategroyServicImpl implements CategroyService {

	@Autowired
	private CategroyDao dao ; 
	@Override
	public ProductCategory findById(Integer categroyId) {
		return dao.findById(categroyId).get();
	}

	@Override
	public List<ProductCategory> findAll() {
		return dao.findAll();
	}

	@Override
	public List<ProductCategory> findByCategroyTypeIn(List<Integer> categroyTypeList) {
		return dao.findByCategoryTypeIn(categroyTypeList);
	}

	@Override
	public ProductCategory save(ProductCategory productCategory) {
		return dao.save(productCategory);
	}

}
