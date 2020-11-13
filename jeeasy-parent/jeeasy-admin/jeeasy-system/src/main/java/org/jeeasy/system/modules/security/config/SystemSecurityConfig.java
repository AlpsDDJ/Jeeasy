package org.jeeasy.system.modules.security.config;

import org.jeeasy.security.service.IJeeasySecurityService;
import org.jeeasy.security.support.JeeasySecurityHandlerProvider;
import org.jeeasy.security.tools.JwtTokenUtil;
import org.jeeasy.system.modules.security.model.JeeasySysUserDetails;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
@Configuration
@DependsOn("SpringUtil")
public class SystemSecurityConfig implements InitializingBean {

    @Autowired
    private JwtTokenUtil<JeeasySysUserDetails> jwtTokenUtil;
    
    @Autowired
    private IJeeasySecurityService<JeeasySysUserDetails> securityService;

    @Override
    public void afterPropertiesSet() throws Exception {
        new JeeasySecurityHandlerProvider<JeeasySysUserDetails>().init("JeeasySystem", jwtTokenUtil, securityService);
    }
}
