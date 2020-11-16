package org.jeeasy.auth.provider;

import org.jeeasy.auth.annotation.AuthMethod;
import org.jeeasy.auth.domain.JeeasyWebAuthenticationDetails;
import org.jeeasy.auth.service.IAuthService;
import org.jeeasy.common.core.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * IAuthService 认证方法匹配
 *
 * @author AlpsDDJ
 * @version v1.0
 * @description AuthServiceProvider
 * @date 2020-11-14
 */
@Component
public class AuthServiceProvider {


    @Autowired
    private List<IAuthService<?>> authServiceList;

//    public static final String DEFAULT_AUTH_METHOD = "system";

    /**
     * 根据方法参数获取对应的 AuthService
     *
     * @param method 认证方法名，通常是前端登录参数 authMethod
     * @return
     */
    public IAuthService<?> getAuthService(String method) {
//        if(Tools.isEmpty(method)){
//            method = DEFAULT_AUTH_METHOD;
//        }
        AtomicReference<IAuthService<?>> authService = new AtomicReference<>();
        authServiceList.forEach(s -> {
            AuthMethod authMethod = AnnotationUtils.getAnnotation(s.getClass(), AuthMethod.class);
            if(Tools.isEmpty(authMethod)){
                return;
            }
            if (authMethod.izDefault() || authMethod.method().equals(method)) {
                authService.set(s);
            }
        });
        IAuthService<?> service = authService.get();
        if (Tools.isEmpty(service)) {
            throw new BadCredentialsException("无匹配验证方式.");
        }
        return service;
    }

    public IAuthService<?> getAuthService(Authentication authentication) {
        JeeasyWebAuthenticationDetails details = (JeeasyWebAuthenticationDetails) authentication.getDetails();
        return getAuthService(details.getAuthMethod());
    }
}
