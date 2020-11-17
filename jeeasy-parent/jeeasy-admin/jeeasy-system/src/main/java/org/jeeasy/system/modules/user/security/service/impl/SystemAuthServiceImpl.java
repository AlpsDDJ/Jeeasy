package org.jeeasy.system.modules.user.security.service.impl;

import lombok.Setter;
import org.jeeasy.auth.domain.IAuthUser;
import org.jeeasy.auth.domain.Permission;
import org.jeeasy.auth.service.IAuthService;
import org.jeeasy.common.cache.tools.RedisUtil;
import org.jeeasy.common.core.constant.CommonConstant;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.ServletUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.db.tools.QueryGenerator;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.mapper.SysUserMapper;
import org.jeeasy.system.modules.user.security.model.SystemAuthUser;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Alps
 */
@Component("systemAuthService")
public class SystemAuthServiceImpl implements IAuthService<SystemAuthUser> {

    @Resource
    SysUserMapper sysUserMapper;

    @Setter
    @Value("${jeeasy.system.enable-captcha}")
    private boolean enableCaptcha = true;

    @Resource
    RedisUtil<String, Object> redisUtil;

    @Override
    @Cacheable(value = CommonConstant.CACHE_USER_KEY, key= "username")
    public SystemAuthUser getAuthUserByUsername(String username) {
        String cacheKey = CommonConstant.CACHE_USER_KEY + username;
        SystemAuthUser cacheUser = (SystemAuthUser) redisUtil.get(cacheKey);
        if (Tools.isNotEmpty(cacheUser)) {
            return cacheUser;
        }
        SysUser sysUser = getSysUserByUsername(username);
        if (Tools.isNotEmpty(sysUser)) {
            SystemAuthUser systemAuthUser = IAuthUser.create(sysUser, SystemAuthUser.class);
            redisUtil.set(cacheKey, systemAuthUser);
            return systemAuthUser;
        }
        return null;
    }

    private SysUser getSysUserByUsername(String username){
        return sysUserMapper.selectOne(QueryGenerator.createWrapper(SysUser.class).lambda().eq(SysUser::getUsername, username));
    }

    @Override
    @CacheEvict(value = CommonConstant.CACHE_USER_KEY, key= "#authentication.principal.toString()")
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

        SysUser sysUser = getSysUserByUsername(username);
        return SysUserUtil.create(sysUser).checkPassword(password);

//        return sysUserMapper.checkPasswordByUserName(username, password);

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
