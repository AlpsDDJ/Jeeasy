package org.jeeasy.common.cache.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description CacheManagerProperties
 * @date 2020-11-17
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "jeeasy.cache.manager")
public class CacheManagerProperties {
    private List<CacheConfig> configs;

    @Setter
    @Getter
    public static class CacheConfig {
        /**
         * cache key
         */
        private String key;
        /**
         * 过期时间，sec
         */
        private long second = 60;
    }
}
