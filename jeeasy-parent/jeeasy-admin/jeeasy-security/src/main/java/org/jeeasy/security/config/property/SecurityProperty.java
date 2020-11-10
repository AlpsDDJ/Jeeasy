package org.jeeasy.security.config.property;

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
    private String[] openApi;

    /**
     * loginPage
     * */
    private String loginUrl = "/login";

    /**
     * loginProcessingUrl
     * */
    private String loginProcessingUrl = "/login";

    /**
     * logoutUrl
     * */
    private String logoutUrl = "/logout";
}
