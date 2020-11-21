package org.jeeasy.auth.service;

import org.jeeasy.auth.annotation.AuthMethod;
import org.jeeasy.auth.domain.IAuthUser;
import org.jeeasy.auth.domain.Permission;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.Authentication;

import java.util.Set;

/**
 * 登录认证服务接口
 *
 * @author AlpsDDJ
 * @version v1.0
 * @description IAuthService
 * @date 2020-11-14
 */
public interface IAuthService<U extends IAuthUser> {

    /**
     * 获取认证类型
     * @return
     */
    default AuthMethod getAuthMethod() {
        try {
//            Type typeArgument = TypeUtil.getTypeArgument(this.getClass());
//            String typeName = typeArgument.getTypeName();
//            Class<Object> objectClass = ClassUtil.loadClass(typeName);
            return AnnotationUtils.getAnnotation(this.getClass(), AuthMethod.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    U getAuthUserByUsername(String username);

    /**
     * 登录验证逻辑
     *
     * @param authentication
     * @return
     */
    boolean verifyLogin(String username, Authentication authentication);

    /**
     * 登录验证逻辑
     *
     * @param authUser
     * @return
     */
//    boolean verifyLogin(U authUser, Authentication authentication);

    /**
     * 根据用户名获取用户角色
     *
     * @param username
     * @return
     */
    Set<String> getRoleSetByUsername(String username);

    /**
     * 根据用户名获取用户权限
     *
     * @param username
     * @return
     */
    Set<String> getPermissionSetByUsername(String username);

    /**
     * 根据用户名获取用户角色
     *
     * @return
     */
    Set<Permission> getAllPermission();

    /**
     * 授权成功后调用 可用于保存登录日志
     *
     * @param authUser 登录用户信息
     * @return
     */
    void onAuthenticationSuccess(IAuthUser authUser);
}
