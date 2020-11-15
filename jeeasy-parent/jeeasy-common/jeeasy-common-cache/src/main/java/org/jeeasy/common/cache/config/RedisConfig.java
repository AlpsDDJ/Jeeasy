package org.jeeasy.common.cache.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author AlpsDDJ
 * @date 2020/11/12
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    @Bean
    @Override
    public KeyGenerator keyGenerator()
    {
        return (o,method,objects)->
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(o.getClass().getName());
            stringBuilder.append(method.getName());
            for (Object obj : objects)
            {
                stringBuilder.append(obj.toString());
            }
            return stringBuilder.toString();
        };
    }
    /**
     *
     * Description: redisTemplate序列化
     * @param factory
     * @author AlpsDDJ
     */
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory)
    {
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<Object, Object>();
        redisTemplate.setConnectionFactory(factory);
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        /*设置value值的序列化*/
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
        redisTemplate.setHashValueSerializer(fastJsonRedisSerializer);
        /*设置key的序列化*/
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}