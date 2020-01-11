package com.imooc.sell.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisLock {
	@Autowired
	private StringRedisTemplate  redisTemplate ;
	
	/**
	 * 加锁
	 * @param key
	 * @param value 当前时间+超时时间
	 * @return
	 */
	public boolean lock(String key,String value) {
		if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
			return true;
		}
		String currentVaule = redisTemplate.opsForValue().get(key);
		//如果锁过期
		if(!StringUtils.isEmpty(currentVaule)&&Long.parseLong(currentVaule)<System.currentTimeMillis()) {
			String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
			//获取上一个锁的时间
			if(!StringUtils.isEmpty(oldValue)&&oldValue.equals(currentVaule)) {
				return true;
			}
		}
		return false;			
	}
	
	/**
	 * 解锁
	 * @param key
	 * @param value
	 */
	public void unLock(String key,String value) {
		try {
			String currentVaule = redisTemplate.opsForValue().get(key);
			if(!StringUtils.isEmpty(currentVaule)&&currentVaule.equals(value)) {
				redisTemplate.opsForValue().getOperations().delete(key);
			}
		} catch (Exception e) {
			log.error("【redis分布式锁】 解锁异常",e);
		}
		
	}

}
