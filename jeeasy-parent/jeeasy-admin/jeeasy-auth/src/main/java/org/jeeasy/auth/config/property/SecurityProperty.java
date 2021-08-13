package org.jeeasy.auth.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 系统权限配置类
 *
 * @author Alps
 */
@Getter
@Setter
@ConfigurationProperties("jeeasy.security")
//@AutoConfigureAfter({RequestMappingHandlerMapping.class, SpringUtil.class})
//@DependsOn("SpringUtil")
public class SecurityProperty {

    /**
     * 开 放 接 口 列 表
     */
    private String[] openApi = new String[0];

    /**
     * loginPage
     */
    private String loginUrl = "/nouser";

    /**
     * loginProcessingUrl
     */
    private String loginProcessingUrl = "/auth/login";

    /**
     * logoutUrl
     */
    private String logoutUrl = "/logout";

    /**
     * logoutUrl
     */
    private Long expiration = 12 * 3600L;

    /**
     * 最大登录设备数 = 0 无限制
     */
    private Integer loginDevicesMax = 0;

}
