package org.jeeasy.system.modules.security.support;

import org.jeeasy.system.modules.security.model.JeeasySysUserDetails;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Describe: 自定义 Security 权限注解实现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Component
public class SecurityPermissionEvaluator implements PermissionEvaluator {

    /**
     * 引 入 服 务
     * */
//    @Resource
//    private SysPowerMapper sysPowerMapper;

    /**
     * Describe: 自定义 Security 权限认证 @HasPermission
     * Param: Authentication
     * Return Boolean
     * */
    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1)
    {
        JeeasySysUserDetails securityUserDetails = (JeeasySysUserDetails) authentication.getPrincipal();
//        List<SysPower> powerList = sysPowerMapper.selectByUsername(securityUserDetails.getUsername());
        Set<String> permissions = new HashSet<>();
//        for (SysPower sysPower :powerList) {
//            permissions.add(sysPower.getPowerCode());
//        }
        if(permissions.contains(o1)){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
