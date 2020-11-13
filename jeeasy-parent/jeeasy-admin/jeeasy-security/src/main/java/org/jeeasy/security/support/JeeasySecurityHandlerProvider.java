package org.jeeasy.security.support;

import org.jeeasy.common.core.tools.SpringUtil;
import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
//import org.jeeasy.security.filter.JwtAuthenticationFilter;
import org.jeeasy.security.process.*;
import org.jeeasy.security.service.IJeeasySecurityService;
import org.jeeasy.security.tools.JwtTokenUtil;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
public class JeeasySecurityHandlerProvider<U extends JeeasyBaseSecurityUserDetails> {

    public void init(String moduleName, JwtTokenUtil<U> jwtTokenUtil, IJeeasySecurityService<U> securityService){
        registerBean(moduleName, securityPermissionEvaluator(securityService));
        registerBean(moduleName, sysUserSecurityAuthenticationProvider(securityService));
//        registerBean(moduleName, jwtAuthenticationFilter(jwtTokenUtil, securityService));
        registerBean(moduleName, securityAccessDeniedHandler());
        registerBean(moduleName, securityAuthenticationEntryPoint());
        registerBean(moduleName, securityAuthenticationFailureHandler());
        registerBean(moduleName, securityAuthenticationSuccessHandler(jwtTokenUtil, securityService));
        registerBean(moduleName, securityLogoutSuccessHandler());
    }

    private void registerBean(String namePrefix, Object obj){
        SpringUtil.registerBean(namePrefix + obj.getClass().getSimpleName(), obj);
    }

//    @Bean
    public JeeasySecurityPermissionEvaluator<U> securityPermissionEvaluator(IJeeasySecurityService<U> securityService){
        return new JeeasySecurityPermissionEvaluator<>(securityService);
    }

//    @Bean
    public JeeasySecurityAuthenticationProvider<U> sysUserSecurityAuthenticationProvider(IJeeasySecurityService<U> securityService){
        return new JeeasySecurityAuthenticationProvider<>(securityService);
    }

//    @Bean
//    public JwtAuthenticationFilter<U> jwtAuthenticationFilter(JwtTokenUtil<U> jwtTokenUtil, IJeeasySecurityService<U> securityService){
//        return new JwtAuthenticationFilter<>(jwtTokenUtil, securityService);
//    }


//    @Bean
    public SecurityAccessDeniedHandler<U> securityAccessDeniedHandler(){
        return new SecurityAccessDeniedHandler<>();
    }

//    @Bean
    public SecurityAuthenticationEntryPoint<U> securityAuthenticationEntryPoint(){
        return new SecurityAuthenticationEntryPoint<>();
    }

//    @Bean
    public SecurityAuthenticationFailureHandler<U> securityAuthenticationFailureHandler(){
        return new SecurityAuthenticationFailureHandler<>();
    }

//    @Bean
    public SecurityAuthenticationSuccessHandler<U> securityAuthenticationSuccessHandler(JwtTokenUtil<U> jwtTokenUtil, IJeeasySecurityService<U> securityService){
        return new SecurityAuthenticationSuccessHandler<>(jwtTokenUtil, securityService);
    }

//    @Bean
    public SecurityLogoutSuccessHandler<U> securityLogoutSuccessHandler(){
        return new SecurityLogoutSuccessHandler<>();
    }

}
