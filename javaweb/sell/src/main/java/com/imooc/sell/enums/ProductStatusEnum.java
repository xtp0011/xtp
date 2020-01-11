package com.imooc.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 * @author 徐太平
 *
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{
	UP(0,"上架"),
	DOWN(1,"下架");
	
	private Integer code;
	
	private String message ;

	private ProductStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	} 
	
}
