package com.imooc.sell.web;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.exception.SellException;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("/weixin")
@Slf4j
public class WeChatController {
	@Autowired
	private WxMpService wxMpService;
	
	@Autowired
	private WxMpService wxOpenService;
	
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	
	@GetMapping("qrAuthorize")
	public String qrAuthorize(@RequestParam("returnUrl") String returnUrl) {
		String url = projectUrlConfig.getWechatOpenAuthorize()+"xtpOpen.com";
		String rediretUrl = wxOpenService.buildQrConnectUrl(url, WxConsts.QRCONNECT_SCOPE_SNSAPI_LOGIN, URLEncoder.encode(returnUrl));
		log.info("【微信网页登陆】 获取code, result={}",rediretUrl );
		return "redirect:"+rediretUrl;
	}
	
	@GetMapping("qrUserInfo")
	public String qrUserInfo(@RequestParam("code")String code,
			@RequestParam("rediretUrl") String rediretUrl) {
		WxMpOAuth2AccessToken accessToken = null;
		try {
			accessToken = wxOpenService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信登陆授权】 {}",e);
			e.printStackTrace();
			throw new SellException(ReslutEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
		}
		String openid = accessToken.getOpenId();
		return "redirect:"+rediretUrl+"?openid"+openid;
	}
	
	@GetMapping("/authorize")
	public String authorize(@RequestParam("returnUrl") String returnUrl) {
		String url =projectUrlConfig.getWechatMpAuthorize()+"xtpMp.com";
		String rediretUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO,URLEncoder.encode(returnUrl));
		log.info("【微信网页授权】 获取code, result={}",rediretUrl );
		return "redirect:"+rediretUrl;
	}
	
	@GetMapping("/userInfo")
	public String userInfo(@RequestParam("code")String code,
			@RequestParam("rediretUrl") String rediretUrl) {
		WxMpOAuth2AccessToken accessToken = null;
		try {
			accessToken = wxMpService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			log.error("【微信网页授权】 {}",e);
			e.printStackTrace();
			throw new SellException(ReslutEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
		}
		String openid = accessToken.getOpenId();
		return "redirect:"+rediretUrl+"?openid"+openid;
	}
}
