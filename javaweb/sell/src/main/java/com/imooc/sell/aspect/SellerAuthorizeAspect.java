package com.imooc.sell.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.imooc.sell.constant.CookieConstant;
import com.imooc.sell.constant.RedisConstant;
import com.imooc.sell.exception.SellerAuthorizeException;
import com.imooc.sell.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Pointcut("execution(public * com.imooc.sell.web.Seller*.*(..))"+
			"&& !execution(public * com.imooc.sell.web.SellerUserController.*(..))")
	public void verify() {}
	
	@Before("verify()")
	public void doVerify() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		//查询cookie
		Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
		if(cookie==null) {
			log.warn("【登陆校验】 cookie中查不到token");
			throw new SellerAuthorizeException();
		}
		String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
		if(StringUtils.isEmpty(tokenValue)) {
			log.warn("【登陆校验】 redis中查不到token");
			throw new SellerAuthorizeException();
		}
	}
		
		
}
