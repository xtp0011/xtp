package com.imooc.sell.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.imooc.sell.dto.CartDto;
import com.imooc.sell.entity.ProductInfo;

/**
 * 商品
 * @author 徐太平
 *
 */
public interface ProductInfoService {
	ProductInfo findByProductId(String productid);
	
	/**
	 * 在架的商品
	 * @return
	 */
	List<ProductInfo> findUpAll();
	
	Page<ProductInfo> findAll(Pageable pageable);
	
	ProductInfo save(ProductInfo productInfo);
	
	//加库存
	void increaseStock(List<CartDto> cartDtoList);
	//减库存
	void decreaseStock(List<CartDto> cartDtoList);

	//上架
	ProductInfo onSale(String productId);
	
	//下架
	ProductInfo offSale(String productId);
}
