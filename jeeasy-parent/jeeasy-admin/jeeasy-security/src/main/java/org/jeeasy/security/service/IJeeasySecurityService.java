package org.jeeasy.security.service;

import org.jeeasy.security.domain.JeeasySecurityPermission;
import org.jeeasy.security.domain.JeeasySecurityUserDetails;
import org.springframework.security.core.Authentication;

import java.util.Set;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
public interface IJeeasySecurityService<U extends JeeasySecurityUserDetails> {

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    U getUserByUsername(String username);

    /**
     * @param authentication
     * @return
     */
    U verifyLogin(Authentication authentication);

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
    Set<JeeasySecurityPermission> getAllPermission();

    /**
     * 授权成功后调用 可用于保存登录日志
     *
     * @param securityUserDetails 登录用户信息
     * @return
     */
    void onAuthenticationSuccess(U securityUserDetails);
}
