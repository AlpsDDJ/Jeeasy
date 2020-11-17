package org.jeeasy.system.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description SystemConfigProperties
 * @date 2020-11-17
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "jeeasy.modules.system")
public class SystemConfigProperties {

    // 验证码开关
    private Boolean enableCaptcha;

}
