package org.jeeasy.common.core.aspect;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.jeeasy.common.core.annotation.controller.RateLimit;
import org.jeeasy.common.core.exception.JeeasyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class RateLimitAspect {
    private final RedisTemplate<String, String> redisTemplate;
    private final HttpServletRequest request;

    public RateLimitAspect(RedisTemplate<String, String> redisTemplate, HttpServletRequest request) {
        this.redisTemplate = redisTemplate;
        this.request = request;
    }

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String ip = request.getRemoteAddr();
        String rateLimitKey = rateLimit.key();
        String key = "rate_limit:" + ip + ":";
        if (StringUtils.EMPTY.equals(rateLimitKey)) {
            key += joinPoint.getSignature().getName();
        } else {
            key += rateLimitKey;
        }

        // 获取剩余的时间窗口
        TimeUnit unit = rateLimit.unit();
        long time = rateLimit.time();
        int max = rateLimit.max();
        Long expire = redisTemplate.getExpire(key, unit);
        
        if (expire == null || expire <= 0) {
            redisTemplate.opsForValue().set(key, "1", time, unit);
        } else {
            String count = redisTemplate.opsForValue().get(key);
            if (count != null) {
                if (Integer.parseInt(count) >= max) {
                    throw new JeeasyException("Too Many Requests");
                }
            }
            redisTemplate.opsForValue().increment(key);
        }

        return joinPoint.proceed();
    }
}
