package org.jeeasy.auth.config;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.auth.annotation.AnonymousAccess;
import org.jeeasy.auth.config.property.SecurityProperty;
import org.jeeasy.auth.domain.Permission;
import org.jeeasy.auth.filter.JwtAuthenticationFilter;
import org.jeeasy.auth.provider.AuthServiceProvider;
import org.jeeasy.common.core.tools.SpringUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Alps
 * @description: 启动基于Spring Security的安全认证
 * @projectName: spring-parent
 * @package: com.yaomy.security.config.BaseSecurityConfig
 * @date: 2019/6/28 15:31
 * @version: 1.0
 */
@Slf4j
@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties({SecurityProperty.class})
//@AutoConfigureAfter({RequestMappingHandlerMapping.class, SpringUtil.class})
@DependsOn("SpringUtil")
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;


    @Resource(name = "anonymousUrls")
    private String[] anonymousUrls;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 去掉 CSRF（Cross-site request forgery）跨站请求伪造,依赖web浏览器，被混淆过的代理人攻击,使用无状态认证时要关闭
        http.csrf().disable()
                // 使用 JWT，使用无状态会话，不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                //配置 Http Basic 验证
                .httpBasic().disable()
                .logout().disable()
                //匿名用户异常拦截处理器

//                .authenticationEntryPoint((HttpServletRequest request, HttpServletResponse response, AuthenticationException e) -> {
//                    R.noUser().responseWrite(response);
//                })
//                .and()

                .authorizeRequests()
                .antMatchers(anonymousUrls).permitAll()
                .anyRequest().authenticated()
                // RBAC 动态 url 认证
//                .anyRequest()
//                .access("@rbacAuthorityService.hasPermission(request,authentication)")
                //认证过的用户访问无权限资源时的处理
                .and().exceptionHandling().accessDeniedHandler((HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) -> {
                    R.noAuth().responseWrite(response);
                });

        List<Permission> allAuthorities = authServiceProvider.getAllAuthorities();
        if(Tools.isNotEmpty(allAuthorities)){
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
            allAuthorities.forEach(permission -> {
                registry.antMatchers(HttpMethod.resolve(permission.getMethod()), permission.getPath()).hasAuthority(permission.getCode());
            });
        }

        // 添加自定义 JWT 过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }


    /**
     * 获取匿名访问地址
     *
     * @return
     */
    @Bean("anonymousUrls")
    public String[] anonymousUrls(SecurityProperty securityProperty) {
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

    /**
     * 解决session失效后 sessionRegistry中session没有同步失效的问题
     *
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

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

    /**
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     *
     * @return 认证管理对象
     */
    @Bean
    public AuthenticationManager getAuthenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 设置加密方式
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
