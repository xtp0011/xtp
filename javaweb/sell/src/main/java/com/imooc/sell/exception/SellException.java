package com.imooc.sell.exception;

import com.imooc.sell.enums.ReslutEnum;

import lombok.Getter;
@Getter
public class SellException extends RuntimeException{
	private Integer code;
	
	public SellException(ReslutEnum reslutEnum) {
		super(reslutEnum.getMessage());
		this.code = reslutEnum.getCode();
	}

	public SellException(Integer code,String message) {
		super(message);
		this.code = code;
	}
}
