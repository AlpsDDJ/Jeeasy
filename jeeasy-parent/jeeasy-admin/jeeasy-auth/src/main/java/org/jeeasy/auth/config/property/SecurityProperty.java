package org.jeeasy.auth.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Describe: 系 统 权 限 配 置 类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Getter
@Setter
@ConfigurationProperties("jeeasy.security")
public class SecurityProperty {

    /**
     * 开 放 接 口 列 表
     * */
    private String[] openApi = new String[0];

    /**
     * loginPage
     * */
    private String loginUrl = "/nouser";

    /**
     * loginProcessingUrl
     * */
    private String loginProcessingUrl = "/login";

    /**
     * logoutUrl
     * */
    private String logoutUrl = "/logout";

    /**
     * logoutUrl
     * */
    private Long expiration = 3600L;

    /**
     * 最大登录设备数 = 0 无限制
     *
     * */
    private Integer loginDevicesMax = 0;
}
