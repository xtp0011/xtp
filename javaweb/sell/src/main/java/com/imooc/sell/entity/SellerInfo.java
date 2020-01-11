package com.imooc.sell.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class SellerInfo {
	@Id
	private String id;
	private String username;
	private String password;
	private String openid;
}
