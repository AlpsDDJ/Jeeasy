package org.jeeasy.sso.config;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaHttpMethod;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import org.jeeasy.common.core.config.property.DictEnumProperty;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.sso.config.property.SaTokenProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

//    /**
//     * 注册 [Sa-Token全局过滤器]
//     */
//    @Bean
//    public SaServletFilter getSaServletFilter() {
//        return new SaServletFilter()
//                // 拦截与排除 path
//                .addInclude("/**").addExclude("/favicon.ico")
//
//                // 全局认证函数
//                .setAuth(obj -> {
//                    // ...
//                })
//
//                // 异常处理函数
//                .setError(e -> {
//                    return R.error(e.getMessage());
//                })
//
//                // 前置函数：在每次认证函数之前执行
//                .setBeforeAuth(obj -> {
//                    // ---------- 设置跨域响应头 ----------
//                    SaHolder.getResponse()
//                            // 允许指定域访问跨域资源
//                            .setHeader("Access-Control-Allow-Origin", "*")
//                            // 允许所有请求方式
//                            .setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE")
//                            // 有效时间
//                            .setHeader("Access-Control-Max-Age", "3600")
//                            // 允许的header参数
//                            .setHeader("Access-Control-Allow-Headers", "*");
//
//                    // 如果是预检请求，则立即返回到前端
//                    SaRouter.match(SaHttpMethod.OPTIONS)
//                            .free(r -> System.out.println("--------OPTIONS预检请求，不做处理"))
//                            .back();
//                })
//                ;
//    }
}
