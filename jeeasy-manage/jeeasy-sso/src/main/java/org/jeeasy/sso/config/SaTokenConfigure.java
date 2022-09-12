package org.jeeasy.sso.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import org.jeeasy.sso.config.property.SaTokenProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * Sa-Token 配置
 *
 * @author wei.yang
 * @date 2022-09-12 12:16
 */
@EnableWebMvc
@Configuration
@EnableConfigurationProperties(SaTokenProperty.class)
public class SaTokenConfigure implements WebMvcConfigurer {

        @Resource
    private SaTokenProperty saTokenProperty;

    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册 Sa-Token 拦截器，打开注解式鉴权功能
        registry.addInterceptor(new SaInterceptor(handle -> {
                    List<SaTokenProperty.MatchRule> rules = saTokenProperty.getRules();
                    rules.forEach(rule -> {
                        SaRouter.match(rule.getPath(), r -> {
                            List<String> roles = rule.getRoles();
                            List<String> permissions = rule.getPermissions();
                            if(CollectionUtil.isNotEmpty(roles)) {
                                StpUtil.checkRoleAnd(ArrayUtil.toArray(roles, String.class));
                            }
                            if(CollectionUtil.isNotEmpty(permissions)) {
                                StpUtil.checkRoleAnd(ArrayUtil.toArray(permissions, String.class));
                            }
                        });
                    });
                }))
                .addPathPatterns(saTokenProperty.getBase())
                .excludePathPatterns(saTokenProperty.getExclude());
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }
}
