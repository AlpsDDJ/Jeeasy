package org.jeeasy.common.doc.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.doc.tools.SwaggerUtil;
import org.jeeasy.common.core.tools.SpringUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @Author scott
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
@ConditionalOnProperty(name = "jeeasy.swagger.enable", havingValue = "true")
@DependsOn("SpringUtil")
public class Swagger2Config implements WebMvcConfigurer, InitializingBean {

    @Resource
    private SwaggerModuleConfiguration moduleConfiguration;

    /**
     * 显示swagger-ui.html文档展示页，还必须注入swagger资源：
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    public void autoConfig() {
        Map<String, SwaggerModule> moduleMap = moduleConfiguration.getModule();
        moduleMap.keySet().forEach(key -> {
            SpringUtil.registerBean(key, SwaggerUtil.createDocket(moduleMap.get(key)));
        });
    }

    @Override
    public void afterPropertiesSet() {
        autoConfig();
    }
}
