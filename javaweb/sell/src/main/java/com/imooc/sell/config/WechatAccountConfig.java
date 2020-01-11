package com.imooc.sell.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix="wechat")
public class WechatAccountConfig {
	
	/**
	 * 公众平台
	 */
	private String mpAppId;
	private String mpAPPSecret;
	
	/**
	 * 开发平台
	 */
	private String openAppId;
	private String openAppSecret ; 
	
	private String mchid;//商户号
	private String mchKey;//商户密钥
	private String keyPath;//商户证书路径
	private String notifyUrl;//微信支付异步通知地址
	
	/**
	 *微信 模板编号
	 */
	private Map<String,String> templateId;
}
