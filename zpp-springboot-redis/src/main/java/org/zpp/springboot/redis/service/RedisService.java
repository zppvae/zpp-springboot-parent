package org.zpp.springboot.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private RedisTemplate redisTemplate;

	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key,value);
	}

	public void set(String key, Object object, Long time) {
		// 存放String 类型
		if (object instanceof String) {
			setString(key, object);
		}
		// 存放 set类型
		if (object instanceof Set) {
			setSet(key, object);
		}
		// 设置有效期 以秒为单位
		stringRedisTemplate.expire(key, time, TimeUnit.SECONDS);
	}

	public void setString(String key, Object object) {
		// 如果是String 类型
		String value = (String) object;
		stringRedisTemplate.opsForValue().set(key, value);
	}

	public void setSet(String key, Object object) {
		Set<String> value = (Set<String>) object;
		for (String oj : value) {
			stringRedisTemplate.opsForSet().add(key, oj);
		}
	}

	public void setStringWithTransaction(String key, Object object) {
		stringRedisTemplate.setEnableTransactionSupport(true);
		// 开启事务
		stringRedisTemplate.multi();
		try {
			// 如果是String 类型
			String value = (String) object;
			stringRedisTemplate.opsForValue().set(key, value);
		} catch (Exception e) {
			// 回滚
			stringRedisTemplate.discard();
		} finally {
			// 提交
			stringRedisTemplate.exec();
		}

	}
	public String getString(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	/**
	 * 判断是否存在key
	 * @param key
	 * @return
	 */
	public boolean exists(final String key){
		return redisTemplate.hasKey(key);
	}

	public boolean remove(final String key){
		if (exists(key)) {
			return redisTemplate.delete(key);
		}
		return false;
	}
}