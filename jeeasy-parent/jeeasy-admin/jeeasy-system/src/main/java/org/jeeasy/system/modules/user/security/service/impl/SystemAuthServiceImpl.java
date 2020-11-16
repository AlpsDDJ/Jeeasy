package org.jeeasy.system.modules.user.security.service.impl;

import lombok.Setter;
import org.jeeasy.auth.annotation.AuthMethod;
import org.jeeasy.auth.domain.IAuthUser;
import org.jeeasy.auth.domain.Permission;
import org.jeeasy.auth.service.IAuthService;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.ServletUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.security.model.SystemAuthUser;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alps
 */
@Service("systemAuthService")
@AuthMethod(value = "system", izDefault = true)
public class SystemAuthServiceImpl implements IAuthService<SystemAuthUser> {

    @Autowired
    ISysUserService sysUserService;

    @Setter
    @Value("${jeeasy.system.enable-captcha}")
    private boolean enableCaptcha = true;

    @Override
    public SystemAuthUser getAuthUserByUsername(String username) {
        SysUser sysUser = sysUserService.getByUserName(username);
        if (Tools.isNotEmpty(sysUser)) {
            return IAuthUser.create(sysUser, SystemAuthUser.class);
        }
        return null;
    }

    @Override
//    @CacheEvict(value = CommonConstant.CACHE_USER_KEY, key= "#authentication.principal")
    public boolean verifyLogin(Authentication authentication) {
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

        return sysUserService.checkPasswordByUserName(username, password);

//        if (Tools.isEmpty(systemAuthUser)) {
//            throw new UsernameNotFoundException("用户名不存在.");
//        }
////
////        if (!systemAuthUser.izEnabled()) {
////            throw new DisabledException("账号已冻结.");
////        }
//        return SysUserUtil.checkPassword(username, password, systemAuthUser.getPassword(), systemAuthUser.getSalt());
//        if(SysUserUtil.create(sysUser).checkPassword(password)){
//        if (SysUserUtil.checkPassword(username, password, systemAuthUser.getPassword(), systemAuthUser.getSalt())) {
//
////            try {
////                JwtUserDetails jwtUserDetails = new JwtUserDetails(username, jwtTokenUtil.getUserLoginNumber(username, securityProperty.getLoginDevicesMax()), LocalDateTime.now(), Tools.getProxyIp());
////                // 保存JWT tokenUser 登录基础信息
////                jwtTokenUtil.saveUserToRedis(jwtUserDetails);
////                jwtTokenUtil.saveUserToRedis(sysUser);
//////                redisUtil.set(jwtTokenUtil.getRedisKey(jwtUserDetails.getUsername(), jwtUserDetails.getNumber()), jwtUserDetails, securityProperty.getExpiration());
////                // 保存 SysUserDetails 用户详细信息
////                redisUtil.set(jwtTokenUtil.getRedisKey(jwtUserDetails.getUsername()), sysUser, securityProperty.getExpiration() * 2);
////            } catch (JeeasyException e) {
////                throw new BadCredentialsException(e.getMessage());
////            }
//
//            systemAuthUser.setPermissions(this.getPermissionSetByUsername(username));
//            systemAuthUser.setRoles(this.getRoleSetByUsername(username));
//            return systemAuthUser;
//        } else {
//            throw new BadCredentialsException("密码错误.");
//        }
    }

//    @Override
//    public boolean verifyLogin(SystemAuthUser authUser, Authentication authentication) {
//        return false;
//    }

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
    public void onAuthenticationSuccess(IAuthUser securityUserDetails) {

    }

}
