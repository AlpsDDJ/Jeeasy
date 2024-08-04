package org.jeeasy.common.core.annotation.controller;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    String key() default StringUtils.EMPTY;

    int max(); // 最大请求次数

    long time() default 1L; // 时间窗口长度，单位秒

    TimeUnit unit() default TimeUnit.SECONDS; // 时间单位
}
