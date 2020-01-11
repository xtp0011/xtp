package com.imooc.sell.from;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class OrderFrom {
	
	@NotEmpty(message="姓名必填")
	private String name;
	
	@NotEmpty(message="手机号码必填")
	private String phone;
	
	@NotEmpty(message="地址必填")
	private String address;
	
	@NotEmpty(message="openid必填")
	private String openid;
	
	@NotEmpty(message="购物车信息必填")
	private String items;
	

}
