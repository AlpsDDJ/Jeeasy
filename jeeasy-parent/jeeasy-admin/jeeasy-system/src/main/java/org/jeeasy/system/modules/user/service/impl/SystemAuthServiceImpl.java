package org.jeeasy.system.modules.user.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jeeasy.auth.annotation.AuthMethod;
import org.jeeasy.common.core.domain.IAuthUser;
import org.jeeasy.auth.domain.Permission;
import org.jeeasy.auth.service.IAuthService;
import org.jeeasy.common.core.config.constant.CommonConstant;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.ServletUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.config.property.SystemConfigProperties;
import org.jeeasy.system.modules.user.domain.SysUser;
import org.jeeasy.system.modules.user.domain.model.SystemAuthUser;
import org.jeeasy.system.modules.user.service.SysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统用户登录认证服务接口
 *
 * @author Alps
 */
@Slf4j
@Component("systemAuthService")
@AuthMethod(value = "system", izDefault = true)
@EnableConfigurationProperties(SystemConfigProperties.class)
public class SystemAuthServiceImpl implements IAuthService<SystemAuthUser> {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SystemConfigProperties properties;

    @Override
    @Cacheable(value = CommonConstant.CACHE_SYS_USER_KEY, key= "#username")
    public SystemAuthUser getAuthUserByUsername(String username) {
        SysUser sysUser = sysUserService.getByUserName(username);
        if (Tools.isNotEmpty(sysUser)) {
            SystemAuthUser authUser = IAuthUser.create(sysUser, SystemAuthUser.class);
            authUser.setPermissionSet(this.getPermissionSetByUsername(username)).setRoleSet(this.getRoleSetByUsername(username));
            return authUser;
//            return IAuthUser.create(sysUser, SystemAuthUser.class);
        }
        return null;
    }

    @Override
    @CacheEvict(value = CommonConstant.CACHE_SYS_USER_KEY, key= "#username")
    public boolean verifyLogin(String username, Authentication authentication) {
        try {
            // 验证码
            if (properties.getEnableCaptcha()) {
                String captcha = ServletUtil.getRequest().getParameter("captcha");
                String captchaKey = ServletUtil.getRequest().getParameter("captchaKey");
                Tools.verifyCaptcha(captchaKey, captcha);
            }
        } catch (JeeasyException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        String password = (String) authentication.getCredentials();
        SysUser sysUser = sysUserService.getByUserName(username);

        if (Tools.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        if(SysUserUtil.create(sysUser).checkPassword(password)){
//            SystemAuthUser authUser = IAuthUser.create(sysUser, SystemAuthUser.class);
//            authUser.setPermissions(this.getPermissionSetByUsername(username)).setRoles(this.getRoleSetByUsername(username));
//            return authUser;
            return true;
        } else {
            throw new BadCredentialsException("密码错误");
        }
    }

    @Override
    public Set<String> getRoleSetByUsername(String username) {
        Set<String> roles = new HashSet<>();
        roles.add("admin");
        return roles;
    }

    @Override
    public Set<String> getPermissionSetByUsername(String username) {
        return new HashSet<>();
    }

    @Override
    public Set<Permission> getAllPermission() {
        return new HashSet<>();
    }

    @Override
    public void onAuthenticationSuccess(IAuthUser authUser) {
        log.info("用户[ {} ]登录成功", authUser.username());
    }

}
