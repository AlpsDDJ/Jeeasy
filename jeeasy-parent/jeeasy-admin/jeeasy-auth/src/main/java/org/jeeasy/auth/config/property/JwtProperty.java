package org.jeeasy.auth.config.property;

import lombok.Data;
import org.jeeasy.common.core.config.constant.CommonConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author AlpsDDJ
 * @date 2020/11/18
 */
@Component
@ConfigurationProperties(prefix = "jeeasy.jwt")
@Data
public class JwtProperty {
    /**
     * jwt 加密 key，默认值：jeeasy.
     */
    private String secret = "jeeasy";

    /**
     * token前缀，默认值 Bearer
     */
    private String prefix = CommonConstant.TOKEN_PREFIX;

    /**
     * jwt 过期时间，默认值：3600000 {@code 1 小时}.
     */
    private Long ttl = 1000L * 60 * 60;

    /**
     * 开启 记住我 之后 jwt 过期时间，默认值 604800000 {@code 7 天}
     */
    private Long remember = 604800000L;

}
