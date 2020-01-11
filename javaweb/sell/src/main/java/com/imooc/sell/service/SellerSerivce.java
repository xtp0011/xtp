package com.imooc.sell.service;

import com.imooc.sell.entity.SellerInfo;

/**
 * 卖家端
 * @author 徐太平
 *
 */
public interface SellerSerivce {
	SellerInfo findByOpenid(String openid);
}
