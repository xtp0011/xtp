package com.imooc.sell.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;

@Component
public class WeChatMpConfig {
	@Autowired
	private WechatAccountConfig accountConfig;
	
	@Bean
	public WxMpService wxMpService() {
		WxMpService mpService = new WxMpServiceImpl();
		mpService.setWxMpConfigStorage(wxMpConfigStorage());
		return mpService;
	}
	@Bean
	public WxMpConfigStorage wxMpConfigStorage() {
		WxMpInMemoryConfigStorage WxMpConfigStorage = new WxMpInMemoryConfigStorage();
		WxMpConfigStorage.setAppId(accountConfig.getMpAppId());
		WxMpConfigStorage.setSecret(accountConfig.getMpAPPSecret());
		return WxMpConfigStorage;
	}
	
}
