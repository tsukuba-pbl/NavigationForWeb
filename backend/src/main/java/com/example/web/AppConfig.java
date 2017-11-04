package com.example.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class AppConfig {
	@Autowired
	private JedisConnectionFactory jedisConnectionFactory;
	
	@Bean
	public <K, V> RedisTemplate<K, V> redisTemplate() {
	    RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
	    redisTemplate.setConnectionFactory(jedisConnectionFactory);
	    redisTemplate.setKeySerializer(new StringRedisSerializer());
	    redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
	    redisTemplate.afterPropertiesSet();
	    return redisTemplate;
	}
	
	@Bean
    public CacheManager cacheManager() {
        return new RedisCacheManager(redisTemplate());
    }
}
