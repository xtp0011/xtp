package com.imooc.sell.web;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.imooc.sell.config.ProjectUrlConfig;
import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.entity.SellerInfo;
import com.imooc.sell.enums.ReslutEnum;
import com.imooc.sell.service.SellerSerivce;
import com.imooc.sell.util.CookieUtil;

@Controller
@RequestMapping("/seller")
public class SellerUserController {
	
	@Autowired
	private SellerSerivce sellerSerivce;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	
	/**
	 * 登陆
	 * @param openid
	 * @param map
	 * @param response
	 * @return
	 */
	@GetMapping("/login")
	public ModelAndView login(@RequestParam("openid") String openid,
			Map<String,Object> map,HttpServletResponse response) {
		//openid和数据库里的数据匹配
		SellerInfo sellerInfo = sellerSerivce.findByOpenid(openid);
		if(sellerInfo==null) {
			map.put("msg", ReslutEnum.LOGIN_FAIL.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error",map);
		}
		//设置token至redis
		String token = UUID.randomUUID().toString();
		Integer expire = RedisConstant.EXPIRE;
		redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token),
				openid,expire,TimeUnit.SECONDS);
		//设置token至cookie
		CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
		return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");
	}
	
	/**
	 * 登出
	 * @param request
	 * @param response
	 * @param map
	 * @return
	 */
	@GetMapping("/logout")
	public ModelAndView logout(HttpServletRequest request,HttpServletResponse response,
			Map<String,Object> map) {
		//1.从cookie查询
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if(cookie!=null) {
			//2.清除redis
			redisTemplate.opsForValue().getOperations().delete(String.format(
					RedisConstant.TOKEN_PREFIX, cookie.getValue()));
		}
		//3.清除cookie
		CookieUtil.set(response,CookieConstant.TOKEN,null,0);
		map.put("msg", ReslutEnum.LOGOUT_SUCCESS.getMessage());
		map.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success",map);
	}

}
