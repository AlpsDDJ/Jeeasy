package org.jeeasy.sso.provider;

import cn.dev33.satoken.context.SaHolder;
import org.jeeasy.common.core.domain.model.AuthUserModel;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.sso.annotation.AuthType;
import org.jeeasy.sso.domain.Permission;
import org.jeeasy.sso.service.IAuthService;
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
     * @return
     */
    public <M extends AuthUserModel> IAuthService<?> getAuthService() {
        String type = SaHolder.getRequest().getParam("type");
        return getAuthService(type);
    }

    /**
     * 根据方法参数获取对应的 AuthService
     *
     * @return
     */
    public <M extends AuthUserModel> IAuthService<?> getAuthService(String type) {
        AtomicReference<IAuthService<?>> authService = new AtomicReference<>();
        authServiceList.forEach(service -> {
            AuthType authType = service.getAuthType();
            if (Tools.isEmpty(authType)) {
                return;
            }
            if (authType.izDefault() || authType.type().getValue().equals(type)) {
                authService.set(service);
            }
        });
        IAuthService<?> service = authService.get();
        if (Tools.isEmpty(service)) {
            throw new JeeasyException("无匹配验证方式");
        }
        return service;
    }

    private String getServiceAuthType(IAuthService<?> service) {
        AuthType authType = service.getAuthType();
        if (Tools.isEmpty(authType)) {
            return null;
        }
        return authType.type().getValue();
    }

//    public <M extends AuthUserModel> IAuthService<?, M> getAuthService(M authentication) {
////        AuthUserFormModel details = (AuthUserFormModel) authentication.getDetails();
//        return getAuthService(authentication.getType(), authentication.getClass());
//    }

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
