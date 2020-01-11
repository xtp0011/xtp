package com.imooc.sell.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

@Component
public class WeChatPayConfig {
	@Autowired
	private WechatAccountConfig accountConfig;
	
	@Bean
	public BestPayServiceImpl BestPayService() {
	
		BestPayServiceImpl bestPayService = new BestPayServiceImpl();
		bestPayService.setWxPayH5Config(wxPayH5Config());
		return bestPayService;
	}
	
	@Bean
	public WxPayH5Config wxPayH5Config() {
		WxPayH5Config wxPayH5Config= new WxPayH5Config();
		wxPayH5Config.setAppId(accountConfig.getMpAppId());
		wxPayH5Config.setAppSecret(accountConfig.getMpAPPSecret());
		wxPayH5Config.setMchId(accountConfig.getMchid());
		wxPayH5Config.setMchKey(accountConfig.getMchKey());
		wxPayH5Config.setKeyPath(accountConfig.getKeyPath());
		wxPayH5Config.setNotifyUrl(accountConfig.getNotifyUrl());
		return wxPayH5Config;
	}
}
