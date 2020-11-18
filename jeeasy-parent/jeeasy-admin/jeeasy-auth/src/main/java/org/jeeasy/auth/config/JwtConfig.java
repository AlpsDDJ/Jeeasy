package org.jeeasy.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author AlpsDDJ
 * @date 2020/11/18
 */
@Component
@ConfigurationProperties(prefix = "jeeasy.jwt")
@Data
public class JwtConfig {
    /**
     * jwt 加密 key，默认值：jeeasy.
     */
    private String secret = "jeeasy";

    /**
     * jwt 过期时间，默认值：600000 {@code 10 分钟}.
     */
    private Long ttl = 600000L;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;

}
