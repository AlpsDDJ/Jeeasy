package org.jeeasy.system.modules.user.security.service.impl;

import lombok.Setter;
import org.jeeasy.common.cache.tools.RedisUtil;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.ServletUtil;
import org.jeeasy.common.core.tools.SpringUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.security.config.property.SecurityProperty;
import org.jeeasy.security.domain.JeeasySecurityPermission;
import org.jeeasy.security.domain.JwtUserDetails;
import org.jeeasy.security.service.IJeeasySecurityService;
import org.jeeasy.security.tools.JwtTokenUtil;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.security.model.SysUserDetails;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Alps
 */
@Component
public class SysUserSecurityServiceImpl implements IJeeasySecurityService<SysUserDetails> {

    @Autowired
    ISysUserService sysUserService;
    private JwtTokenUtil<SysUserDetails> jwtTokenUtil = SpringUtil.getBean(JwtTokenUtil.class);

    private RedisUtil<String, Object> redisUtil = SpringUtil.getBean(RedisUtil.class);
    private SecurityProperty securityProperty = SpringUtil.getBean(SecurityProperty.class);

    @Setter
    @Value("${jeeasy.system.enable-captcha}")
    private boolean enableCaptcha = true;

    @Override
    public SysUserDetails getUserByUsername(String username) {
        SysUser sysUser = sysUserService.getByUserName(username);
        if (Tools.isNotEmpty(sysUser)) {
            return SysUserDetails.create(sysUser);
        }
        return null;
    }

    @Override
    public SysUserDetails verifyLogin(Authentication authentication) {
        try {
            // 验证码
            if (enableCaptcha) {
                String captcha = ServletUtil.getRequest().getParameter("captcha");
                String captchaKey = ServletUtil.getRequest().getParameter("captchaKey");
                Tools.verifyCaptcha(captchaKey, captcha);
            }
        } catch (JeeasyException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        SysUserDetails sysUser = this.getUserByUsername(username);

        if (Tools.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在.");
        }

        if (!sysUser.izEnabled()) {
            throw new DisabledException("账号已冻结.");
        }
//        if(SysUserUtil.create(sysUser).checkPassword(password)){
        if (SysUserUtil.checkPassword(username, password, sysUser.getPassword(), sysUser.getSalt())) {

            try {
                JwtUserDetails jwtUserDetails = new JwtUserDetails(username, jwtTokenUtil.getUserLoginNumber(username, securityProperty.getLoginDevicesMax()), LocalDateTime.now(), Tools.getProxyIp());
                // 保存JWT tokenUser 登录基础信息
                jwtTokenUtil.saveUserToRedis(jwtUserDetails);
                jwtTokenUtil.saveUserToRedis(sysUser);
//                redisUtil.set(jwtTokenUtil.getRedisKey(jwtUserDetails.getUsername(), jwtUserDetails.getNumber()), jwtUserDetails, securityProperty.getExpiration());
                // 保存 SysUserDetails 用户详细信息
                redisUtil.set(jwtTokenUtil.getRedisKey(jwtUserDetails.getUsername()), sysUser, securityProperty.getExpiration() * 2);
            } catch (JeeasyException e) {
                throw new BadCredentialsException(e.getMessage());
            }


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
    public void onAuthenticationSuccess(SysUserDetails securityUserDetails) {

    }

}
