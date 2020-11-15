package org.jeeasy.auth.config.property;

import cn.hutool.core.util.ArrayUtil;
import lombok.Getter;
import lombok.Setter;
import org.jeeasy.auth.annotation.AnonymousAccess;
import org.jeeasy.common.core.tools.SpringUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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


    /**
     * 获取匿名访问地址
     *
     * @return
     */
    public String[] getAnonymousUrls() {
        // 搜寻 匿名标记 url： PreAuthorize("hasAnyRole('anonymous')") 和 PreAuthorize("@el.check('anonymous')") 和 AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = SpringUtil.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
            if (null != preAuthorize && preAuthorize.value().toLowerCase().contains("anonymous")) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            } else if (null != anonymousAccess && null == preAuthorize) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
        }
        String[] anonymousUrlArr = ArrayUtil.append(this.getOpenApi(), this.getLoginUrl(), this.getLoginProcessingUrl());
        return ArrayUtil.addAll(anonymousUrls.toArray(new String[0]), anonymousUrlArr);
    }
}