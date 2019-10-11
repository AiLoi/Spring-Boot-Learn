package com.concurrency.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * @program: redis
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 12:07
 **/
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis")
public class SpringCacheRedisConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory) {
        //序列化器
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        //解决查询缓存转换异常的问题
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()  //启用默认配置
                .entryTtl(Duration.ofMinutes(10)) //10分钟超时
                .disableKeyPrefix() //禁用前缀
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer)) //key序列化
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer)) //value序列化
                .disableCachingNullValues(); //可以为空

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .build();
    }
}

