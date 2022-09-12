package org.jeeasy.common.core.config;

import org.jeeasy.common.core.annotation.controller.ApiController;
import org.jeeasy.common.core.annotation.controller.ManageController;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置统一的接口访问路径的前缀
 *
 * @author wei.yang
 * @date 2022-09-12 16:52
 */
@Configuration
public class JeeasyWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                // api接口
                .addPathPrefix("/api",c -> c.isAnnotationPresent(ApiController.class))
                // 后管接口
                .addPathPrefix("/manage",c -> c.isAnnotationPresent(ManageController.class));
    }
}
