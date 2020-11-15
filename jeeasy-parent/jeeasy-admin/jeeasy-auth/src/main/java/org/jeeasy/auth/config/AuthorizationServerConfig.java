//package org.jeeasy.auth.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.AutoConfigureAfter;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//
///**
// * OAuth2 授权服务器配置
// *
// * @author AlpsDDJ
// * @version v1.0
// * @description AuthorizationServerConfig
// * @date 2020-11-15
// */
//@Configuration
//@EnableAuthorizationServer
//@AutoConfigureAfter(AuthorizationServerEndpointsConfigurer.class)
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
////    @Autowired
////    private TokenGranter tokenGranter;
//
//    @Autowired
//    private TokenStore tokenStore;
//
//
//    /**
//     * 注入authenticationManager 来支持 password grant type
//     */
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    /**
//     * 配置身份认证器，配置认证方式，TokenStore，TokenGranter，OAuth2RequestFactory
//     *
//     * @param endpoints
//     */
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//        endpoints.tokenStore(tokenStore)
//                .authenticationManager(authenticationManager);
////                .userDetailsService(userDetailsService)
////                .authorizationCodeServices(authorizationCodeServices)
////                .exceptionTranslator(webResponseExceptionTranslator)
////                .tokenGranter(tokenGranter);
//    }
//}
