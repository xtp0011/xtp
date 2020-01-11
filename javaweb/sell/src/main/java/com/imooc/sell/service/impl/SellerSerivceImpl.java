package com.imooc.sell.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.imooc.sell.dao.SellerInfoDao;
import com.imooc.sell.entity.SellerInfo;
import com.imooc.sell.service.SellerSerivce;

@Service
public class SellerSerivceImpl implements SellerSerivce {

	@Autowired
	private SellerInfoDao sellerInfoDao;
	@Override
	public SellerInfo findByOpenid(String openid) {
		return sellerInfoDao.findByOpenid(openid);
	}

}
