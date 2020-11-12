package org.jeeasy.system.modules.security.service.impl;

import org.jeeasy.security.service.JeeasyUserDetailsService;
import org.jeeasy.system.modules.security.model.JeeasySysUserDetails;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Alps
 */
@Component
public class SysUserSecurityUserDetailsServiceImpl implements JeeasyUserDetailsService<ISysUserService, JeeasySysUserDetails> {

    @Autowired
    ISysUserService sysUserService;

    @Override
    public ISysUserService getService() {
        return sysUserService;
    }
}
