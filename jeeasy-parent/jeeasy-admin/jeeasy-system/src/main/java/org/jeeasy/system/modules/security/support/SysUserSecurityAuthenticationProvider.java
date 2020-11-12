package org.jeeasy.system.modules.security.support;

import lombok.Setter;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.ServletUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.security.service.JeeasyUserDetailsService;
import org.jeeasy.system.modules.security.model.JeeasySysUserDetails;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Describe: 自定义 Security 登陆认证实现
 *
 * @author Alps
 */
@Component
public class SysUserSecurityAuthenticationProvider implements AuthenticationProvider {

    /**
     * 用户详情服务
     */
    @Resource
    private JeeasyUserDetailsService<ISysUserService, JeeasySysUserDetails> securityUserDetailsService;

    @Setter
    @Value("${jeeasy.system.enable-captcha}")
    private boolean enableCaptcha = true;


    /**
     * Describe: 自定义 Security 登陆逻辑
     * Param: Authentication
     * Return Authentication
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        JeeasySysUserDetails userInfo = securityUserDetailsService.loadUserByUsername(username);
        String captcha = ServletUtil.getRequest().getParameter("captcha");
        String captchaKey = ServletUtil.getRequest().getParameter("captchaKey");
        if (enableCaptcha && Tools.verifyCaptcha(captchaKey, captcha)) {
            throw new JeeasyException("验证码错误");
        }
        if (Tools.isEmpty(userInfo)) {
            throw new UsernameNotFoundException("用户名不存在.");
        }
        if (!userInfo.isEnabled()) {
            throw new DisabledException("账号已冻结.");
        }
        if(SysUserUtil.create(userInfo).checkPassword(password)){
//        if (SysUserUtil.checkPassword(username, password, userInfo.getSalt())) {
            return new UsernamePasswordAuthenticationToken(userInfo, password, userInfo.getAuthorities());
        } else {
            throw new BadCredentialsException("密码错误.");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
