package org.jeeasy.auth.config;

import org.jeeasy.auth.enhancer.UserTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 注入认证所需的bean
 *
 * @author AlpsDDJ
 * @version v1.0
 * @description AuthBeanConfig
 * @date 2020-11-14
 */
@Configuration
public class AuthBeanConfig {

    /**
     * Redis工厂类
     */
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    /**
     * @Description 自定义生成令牌token
     * @Date 2019/7/9 19:58
     * @Version  1.0
     */
    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new UserTokenEnhancer();
    }

    /**
     * @Description OAuth2 token持久化接口初始化
     * @Date 2019/7/9 17:45
     * @Version  1.0
     */
    @Bean
    public TokenStore tokenStore() {
        //token保存在内存中（也可以保存在数据库、Redis中）。
        //如果保存在中间件（数据库、Redis），那么资源服务器与认证服务器可以不在同一个工程中。
        //注意：如果不保存access_token，则没法通过access_token取得用户信息
        //return new InMemoryTokenStore();
        return new RedisTokenStore(redisConnectionFactory);
    }
    /**
     * @Description Spring security认证Bean
     * @Date 2019/7/4 17:39
     * @Version  1.0
     */
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        AuthenticationProvider authenticationProvider = new UserAuthenticationProvider();
//        return authenticationProvider;
//    }
//    @Bean
//    public AuthenticationProvider smsAuthenticationProvider(){
//        AuthenticationProvider authenticationProvider = new UserSmsAuthenticationProvider();
//        return authenticationProvider;
//    }
    /**
     * @Description 自定义加密
     * @Date 2019/7/10 15:07
     * @Version  1.0
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
