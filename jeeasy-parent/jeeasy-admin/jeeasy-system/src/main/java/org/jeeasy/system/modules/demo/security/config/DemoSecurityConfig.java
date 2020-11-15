//package org.jeeasy.system.modules.demo.security.config;
//
//import org.jeeasy.security.service.IJeeasySecurityService;
//import org.jeeasy.security.support.JeeasySecurityHandlerProvider;
//import org.jeeasy.security.tools.JwtTokenUtil;
//import org.jeeasy.system.modules.demo.security.model.DemoUserDetails;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//
///**
// * @author AlpsDDJ
// * @date 2020/11/13
// */
////@Configuration
////@DependsOn("SpringUtil")
//public class DemoSecurityConfig implements InitializingBean {
//
//    @Autowired
//    private JwtTokenUtil<DemoUserDetails> jwtTokenUtil;
//
//    @Autowired
//    private IJeeasySecurityService<DemoUserDetails> securityService;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        new JeeasySecurityHandlerProvider<DemoUserDetails>().init("DemoUser", jwtTokenUtil, securityService);
//    }
//}
