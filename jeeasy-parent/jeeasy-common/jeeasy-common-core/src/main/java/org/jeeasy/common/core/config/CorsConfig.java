package org.jeeasy.common.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author AlpsDDJ
 * @date 2020/11/10
 */
@Configuration
@ConditionalOnProperty(name = "jeeasy.cors.enable", havingValue = "false")
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 允许跨域访问的路径
     */
    @Value("${jeeasy.cors.mapping}")
    private final String mapping = "/**";

    /**
     * 允许跨域访问的源
     */
    @Value("${jeeasy.cors.allowedOrigins}")
    private final String allowedOrigins = "*";

    /**
     * 允许请求方法
     */
    @Value("${jeeasy.cors.allowedMethods}")
    private final String[] allowedMethods = new String[]{"POST", "GET", "PUT", "OPTIONS", "DELETE"};

    /**
     * 预检间隔时间
     */
    @Value("${jeeasy.cors.maxAge}")
    private final Long maxAge = 168000L;

    /**
     * 允许头部设置
     */
    @Value("${jeeasy.cors.allowedHeaders}")
    private final String allowedHeaders = "*";

    /**
     * 是否发送cookie
     */
    @Value("${jeeasy.cors.allowCredentials}")
    private final Boolean allowCredentials = true;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(mapping)
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods)
                .maxAge(maxAge)
                .allowedHeaders(allowedHeaders)
                .allowCredentials(allowCredentials);
    }
}
