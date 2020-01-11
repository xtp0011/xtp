package com.imooc.sell.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.sell.entity.ProductInfo;

public interface ProductInfoDao extends JpaRepository<ProductInfo,String> {
	List<ProductInfo> findByProductStatus(Integer productStatus);
}
