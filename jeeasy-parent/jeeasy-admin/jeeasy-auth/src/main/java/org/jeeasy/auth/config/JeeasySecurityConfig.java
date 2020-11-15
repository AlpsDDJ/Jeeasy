package org.jeeasy.auth.config;

import cn.hutool.core.util.ArrayUtil;
import org.jeeasy.auth.annotation.AnonymousAccess;
import org.jeeasy.auth.config.property.SecurityProperty;
import org.jeeasy.common.core.tools.SpringUtil;
import org.jeeasy.common.core.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 启动基于Spring Security的安全认证
 * @ProjectName: spring-parent
 * @Package: com.yaomy.security.config.BaseSecurityConfig
 * @Date: 2019/6/28 15:31
 * @Version: 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperty.class)
public class JeeasySecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    UserAuthenticationEntryPoint authenticationEntryPoint;
//    @Autowired
//    UserAuthenticationSuccessHandler authenticationSuccessHandler;
//    @Autowired
//    UserAuthenticationFailureHandler authenticationFailureHandler;
//    @Autowired
//    UserLogoutSuccessHandler logoutSuccessHandler;
//    @Autowired
//    UserAccessDeniedHandler accessDeniedHandler;
//    @Autowired
//    private TokenAuthenticationFilter tokenAuthenticationFilter;
//    @Autowired
//    private AuthUserDetailsServiceImpl authUserDetailsService;

    @Autowired
    private SecurityProperty securityProperty;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String aaa = "123";

        http
                // 去掉 CSRF（Cross-site request forgery）跨站请求伪造,依赖web浏览器，被混淆过的代理人攻击,使用无状态认证时要关闭
                .csrf().disable()
                // 使用 JWT，使用无状态会话，不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
                //配置 Http Basic 验证
                .httpBasic()
                //匿名用户异常拦截处理器
                .authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException e) -> {
                    R.noUser().responseWrite(response);
                })
                .and()
                .authorizeRequests()
                /**
                 * ant路径风格有三种通配符：[?]匹配任何单字符；[*]匹配0或者任意数量的字符；[**]匹配0或更多的目录
                 */
                .antMatchers(getAnonymousUrls()).permitAll()
                .anyRequest().authenticated()
                // RBAC 动态 url 认证
//                .access("@rbacServiceImpl.hasPermission(request, authentication)")
                .and()
                //指定支持基于表单的身份验证。如果未指定FormLoginConfigurer#loginPage(String)，则将生成默认登录页面
                .formLogin()
                //自定义登录页url,默认为/login
                .loginPage(securityProperty.getLoginUrl())
                //登录请求拦截的url,也就是form表单提交时指定的action
                .loginProcessingUrl(securityProperty.getLoginProcessingUrl())
                .authenticationDetailsSource(authenticationDetailsSource)
                //用户名的请求字段 username
//                    .usernameParameter("username")
                // 密码的请求字段 默认为password
//                    .passwordParameter("password")
                // 登录成功
                .successHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                    R.ok().setMessage("登录成功.").responseWrite(response);
                })
                // 登录失败
                .failureHandler((HttpServletRequest request, HttpServletResponse response, AuthenticationException e) -> {
                    R.error(e.getMessage()).responseWrite(response);
                })
                //无条件允许访问
                .permitAll()
                .and()
                .logout()
                // 注销登录
                .logoutSuccessHandler((HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
                    R.ok("注销成功.").responseWrite(response);
                })
                .permitAll()
                .and()
                //认证过的用户访问无权限资源时的处理
                .exceptionHandling().accessDeniedHandler((HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) -> {

        });
//                .and()
//                    //将JWT Token Filter验证配置到Spring Security
//                    .addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 记住我
        http.rememberMe().rememberMeParameter("remember-me").tokenValiditySeconds(60);

    }

    /**
     * 获取匿名访问地址
     *
     * @return
     */
    private String[] getAnonymousUrls() {
        // 搜寻 匿名标记 url： PreAuthorize("hasAnyRole('anonymous')") 和 PreAuthorize("@el.check('anonymous')") 和 AnonymousAccess
        Map<RequestMappingInfo, HandlerMethod> handlerMethodMap = SpringUtil.getBean(RequestMappingHandlerMapping.class).getHandlerMethods();
        Set<String> anonymousUrls = new HashSet<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> infoEntry : handlerMethodMap.entrySet()) {
            HandlerMethod handlerMethod = infoEntry.getValue();
            AnonymousAccess anonymousAccess = handlerMethod.getMethodAnnotation(AnonymousAccess.class);
            PreAuthorize preAuthorize = handlerMethod.getMethodAnnotation(PreAuthorize.class);
            if (null != preAuthorize && preAuthorize.value().toLowerCase().contains("anonymous")) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            } else if (null != anonymousAccess && null == preAuthorize) {
                anonymousUrls.addAll(infoEntry.getKey().getPatternsCondition().getPatterns());
            }
        }
        String[] anonymousUrlArr = ArrayUtil.append(securityProperty.getOpenApi(), securityProperty.getLoginUrl(), securityProperty.getLoginProcessingUrl());
        return ArrayUtil.addAll(anonymousUrls.toArray(new String[0]), anonymousUrlArr);
    }

//    @Override
//    public UserDetailsService userDetailsService(){
//        return authUserDetailsService;
//    }

    /**
     * @Description JWT加密算法
     * @Date 2019/7/4 17:38
     * @Version 1.0
     */
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    /**
//     * @Description Spring security认证Bean
//     * @Date 2019/7/4 17:39
//     * @Version  1.0
//     */
//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        AuthenticationProvider authenticationProvider = new UserAuthenticationProvider();
//        return authenticationProvider;
//    }

    /**
     * @Description Spring Security认证服务中的相关实现重新定义
     * @Date 2019/7/4 17:40
     * @Version 1.0
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
        auth.authenticationProvider(authenticationProvider);
    }
}
