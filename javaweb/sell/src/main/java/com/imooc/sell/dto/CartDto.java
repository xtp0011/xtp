package com.imooc.sell.dto;

import lombok.Data;

@Data
public class CartDto {
	
	public CartDto(String productId, Integer productQuantity) {
		super();
		this.productId = productId;
		this.productQuantity = productQuantity;
	}

	/**商品编号*/
	private String productId;
	
	/**数量*/
	private Integer productQuantity;
}
