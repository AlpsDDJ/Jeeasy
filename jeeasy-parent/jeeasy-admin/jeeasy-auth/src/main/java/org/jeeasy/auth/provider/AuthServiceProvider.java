package org.jeeasy.auth.provider;

import org.jeeasy.auth.annotation.AuthMethod;
import org.jeeasy.auth.vo.AuthUserFormModel;
import org.jeeasy.auth.domain.Permission;
import org.jeeasy.auth.service.IAuthService;
import org.jeeasy.common.core.tools.Tools;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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


    @Resource
    private List<IAuthService<?>> authServiceList;

    /**
     * 根据方法参数获取对应的 AuthService
     *
     * @param method 认证方法名，通常是前端登录参数 authMethod
     * @return
     */
    public IAuthService<?> getAuthService(String method) {
        AtomicReference<IAuthService<?>> authService = new AtomicReference<>();
        authServiceList.forEach(service -> {
            AuthMethod authMethod = service.getAuthMethod();
            if (Tools.isEmpty(authMethod)) {
                return;
            }
            if (authMethod.izDefault() || authMethod.method().equals(method)) {
                authService.set(service);
            }
        });
        IAuthService<?> service = authService.get();
        if (Tools.isEmpty(service)) {
            throw new BadCredentialsException("无匹配验证方式");
        }
        return service;
    }

    private String getServiceAuthMethod(IAuthService<?> service) {
        AuthMethod authMethod = service.getAuthMethod();
        if (Tools.isEmpty(authMethod)) {
            return null;
        }
        return authMethod.method();
    }

    public IAuthService<?> getAuthService(Authentication authentication) {
        AuthUserFormModel details = (AuthUserFormModel) authentication.getDetails();
        return getAuthService(details.getAuthMethod());
    }

    public List<Permission> getAllAuthorities() {
        List<Permission> allAuthorities = new ArrayList<>();
        authServiceList.forEach(service -> {
            Set<Permission> allPermission = service.getAllPermission();
            if (Tools.isNotEmpty(allPermission)) {
                allAuthorities.addAll(allPermission);
            }
//            if(Tools.isNotEmpty(allAuthorities)){
//                allPermission.forEach(permission -> {
//                    allAuthorities.add(permission.getMethod(), permission);
//                });
//            }
        });
        return allAuthorities;
    }
}
