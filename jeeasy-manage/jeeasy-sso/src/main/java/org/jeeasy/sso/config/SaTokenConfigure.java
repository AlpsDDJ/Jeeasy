package org.jeeasy.sso.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import org.jeeasy.common.core.config.constant.CommonConstant;
import org.jeeasy.sso.config.property.SaTokenProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        List<String> exclude = saTokenProperty.getExclude();
        exclude.addAll(saTokenProperty.getCommonExclude());
        registry.addInterceptor(new SaInterceptor(handle -> {
                    // 匹配所有路径，如果不属于排除列表，则进行登录检查
                    StpUtil.checkLogin();
                    // 遍历权限规则列表
                    List<SaTokenProperty.MatchRule> rules = saTokenProperty.getRules();
                    rules.forEach(rule -> {
                        // 根据路径进行匹配
                        SaRouter.match(rule.getPath(), r -> {
                            // 判断是否需要进行角色和权限检查
                            List<String> roles = rule.getRoles();
                            List<String> permissions = rule.getPermissions();
                            if (CollectionUtil.isNotEmpty(roles)) {
                                StpUtil.checkRoleAnd(ArrayUtil.toArray(roles, String.class));
                            }
                            if (CollectionUtil.isNotEmpty(permissions)) {
                                StpUtil.checkRoleAnd(ArrayUtil.toArray(permissions, String.class));
                            }
                        });
                    });
                }))
                .addPathPatterns(saTokenProperty.getBase())
                .excludePathPatterns(exclude);
        // 注册 MyInterceptor 并拦截所有路径
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
    }


    // Sa-Token 参数配置，参考文档：https://sa-token.cc
    // 此配置会覆盖 application.yml 中的配置
    @Bean
    @Primary
    public SaTokenConfig getSaTokenConfigPrimary() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName(CommonConstant.X_ACCESS_TOKEN);             // token名称 (同时也是cookie名称)
        config.setTimeout(30 * 24 * 60 * 60);       // token有效期，单位s 默认30天
        config.setActivityTimeout(-1);              // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setIsConcurrent(true);               // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsShare(false);                    // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setTokenStyle("random-64");               // token风格
        config.setIsLog(true);                     // 是否输出操作日志
        return config;
    }
}
