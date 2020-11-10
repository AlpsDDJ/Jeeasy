package org.jeeasy.system.modules.security.support;

import org.jeeasy.system.modules.security.service.impl.SecurityUserDetailsService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Describe: 自定义 Security 登陆认证实现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Component
public class SecurityAuthenticationProvider implements AuthenticationProvider {

    /**
     * 用户详情服务
     * */
    @Resource
    private SecurityUserDetailsService securityUserDetailsService;

    /**
     * 密码加密服务
     * */
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * Describe: 自定义 Security 登陆逻辑
     * Param: Authentication
     * Return Authentication
     * */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserDetails userInfo = securityUserDetailsService.loadUserByUsername(username);
//        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
//        if(!lineCaptcha.verify(ServletUtil.getRequest().getParameter("captcha"))){
//            throw new CaptchaException(" captcha is bad ");
//        }
        if(!passwordEncoder.matches(password,userInfo.getPassword())){
            throw new BadCredentialsException(" Password Not Found ");
        }
        if(!userInfo.isEnabled()){
            throw new DisabledException(" account is disable ");
        }
        return new UsernamePasswordAuthenticationToken(userInfo,password,userInfo.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
