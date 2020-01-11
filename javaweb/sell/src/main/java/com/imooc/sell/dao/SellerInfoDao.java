package com.imooc.sell.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imooc.sell.entity.SellerInfo;

public interface SellerInfoDao extends JpaRepository<SellerInfo, String>{
	SellerInfo findByOpenid(String openid);
}
