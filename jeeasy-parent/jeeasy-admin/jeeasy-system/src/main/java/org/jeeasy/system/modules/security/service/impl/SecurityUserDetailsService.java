package org.jeeasy.system.modules.security.service.impl;

import org.jeeasy.security.service.JeeasyUserDetailsService;
import org.jeeasy.system.modules.security.model.JeeasySysUserDetails;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.springframework.stereotype.Component;

/**
 * Describe: Security 用户服务
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
@Component
public class SecurityUserDetailsService extends JeeasyUserDetailsService<ISysUserService, JeeasySysUserDetails> {

//    @Resource
//    private ISysUserService sysUserService;

//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        SysUser sysUser = service.getByUserName(s);
//        if (sysUser == null) {
//            throw new UsernameNotFoundException("Account Not");
//        }
//        JeeasySysUserDetails securityUserDetails = new JeeasySysUserDetails();
//        securityUserDetails.setId(sysUser.getId());
//        securityUserDetails.setRealName(sysUser.getRealName());
//        securityUserDetails.setUsername(sysUser.getUsername());
//        securityUserDetails.setPassword(sysUser.getPassword());
////        securityUserDetails.getUser().setEnable(sysUser.getEnable());
//        return securityUserDetails;
//    }
}
