package org.jeeasy.system.modules.security.service.impl;

import lombok.Setter;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.ServletUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.security.domain.JeeasySecurityPermission;
import org.jeeasy.security.service.IJeeasySecurityService;
import org.jeeasy.system.modules.security.model.JeeasySysUserDetails;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Alps
 */
@Component
public class SysUserSecurityServiceImpl implements IJeeasySecurityService<JeeasySysUserDetails> {

    @Autowired
    ISysUserService sysUserService;

    @Setter
    @Value("${jeeasy.system.enable-captcha}")
    private boolean enableCaptcha = true;

    @Override
    public JeeasySysUserDetails getUserByUsername(String username) {
        SysUser sysUser = sysUserService.getByUserName(username);
        if(Tools.isNotEmpty(sysUser)){
            return JeeasySysUserDetails.create(sysUser);
        }
        return null;
    }

    @Override
    public JeeasySysUserDetails verifyLogin(Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        JeeasySysUserDetails sysUser = this.getUserByUsername(username);

        String captcha = ServletUtil.getRequest().getParameter("captcha");
        String captchaKey = ServletUtil.getRequest().getParameter("captchaKey");
        if (enableCaptcha && Tools.verifyCaptcha(captchaKey, captcha)) {
            throw new JeeasyException("验证码错误");
        }
        if (Tools.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在.");
        }
        if (!sysUser.isEnabled()) {
            throw new DisabledException("账号已冻结.");
        }
//        if(SysUserUtil.create(sysUser).checkPassword(password)){
        if (SysUserUtil.checkPassword(username, password, sysUser.getPassword(), sysUser.getSalt())) {
            return sysUser;
        } else {
            throw new BadCredentialsException("密码错误.");
        }
    }

    @Override
    public Set<String> getRoleSetByUsername(String username) {
        return null;
    }

    @Override
    public Set<String> getPermissionSetByUsername(String username) {
        return null;
    }

    @Override
    public Set<JeeasySecurityPermission> getAllPermission() {
        return null;
    }

    @Override
    public void onAuthenticationSuccess(JeeasySysUserDetails securityUserDetails) {

    }

}
