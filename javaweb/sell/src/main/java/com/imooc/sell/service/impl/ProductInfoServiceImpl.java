package com.imooc.sell.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imooc.sell.dao.ProductInfoDao;
import com.imooc.sell.dto.CartDto;
import com.imooc.sell.entity.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.service.ProductInfoService;

/**
 * 商品
 * @author 徐太平
 *
 */
@Service
//@CacheConfig(cacheNames="product")
public class ProductInfoServiceImpl implements ProductInfoService{

	@Autowired 
	private ProductInfoDao dao;
	
	@Override
	//@Cacheable(key="123")
	public ProductInfo findByProductId(String productid) {
		return dao.findById(productid).get();
	}

	@Override
	public List<ProductInfo> findUpAll() {
		return dao.findByProductStatus(ProductStatusEnum.UP.getCode());
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	//@CachePut(key="123")
	public ProductInfo save(ProductInfo productInfo) {
		return dao.save(productInfo);
	}

	@Override
	@Transactional
	public void increaseStock(List<CartDto> cartDtoList) {
		for (CartDto cartDto : cartDtoList) {
			ProductInfo productInfo = dao.findById(cartDto.getProductId()).get();
			if(productInfo==null) {
				throw new SellException(ReslutEnum.PRODUCT_NOT_EXIST);
			}
			Integer result = productInfo.getProductStock()+cartDto.getProductQuantity();
			productInfo.setProductStock(result);
			dao.save(productInfo);
		}
		
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDto> cartDtoList) {
		for (CartDto cartDto : cartDtoList) {
			ProductInfo productInfo = dao.findById(cartDto.getProductId()).get();
			if(productInfo==null) {
				throw new SellException(ReslutEnum.PRODUCT_NOT_EXIST);
			}
			Integer result = productInfo.getProductStock()-cartDto.getProductQuantity();
			if(result<0) {
				throw new SellException(ReslutEnum.PROUDCT_STOCK_ERROR);
			}
			productInfo.setProductStock(result);
			dao.save(productInfo);
		}
	}

	@Override
	public ProductInfo onSale(String productId) {
		ProductInfo productInfo = dao.findById(productId).get();
		if(productInfo==null) {
			throw new SellException(ReslutEnum.PRODUCT_NOT_EXIST);
		}
		if(productInfo.getProductStatusEnum()==ProductStatusEnum.UP) {
			throw new SellException(ReslutEnum.PRODUCT_STATUS_ERROR);
		}
		//更新
		productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
		return dao.save(productInfo);
	}

	@Override
	public ProductInfo offSale(String productId) {
		ProductInfo productInfo = dao.findById(productId).get();
		if(productInfo==null) {
			throw new SellException(ReslutEnum.PRODUCT_NOT_EXIST);
		}
		if(productInfo.getProductStatusEnum()==ProductStatusEnum.DOWN) {
			throw new SellException(ReslutEnum.PRODUCT_STATUS_ERROR);
		}
		//更新
		productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
		return dao.save(productInfo);
	}

}
