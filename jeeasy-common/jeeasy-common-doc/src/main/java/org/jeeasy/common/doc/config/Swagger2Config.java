package org.jeeasy.common.doc.config;

import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.tools.SpringUtil;
import org.jeeasy.common.doc.tools.SwaggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.List;


/**
 * @Author scott
 */
@Slf4j
@Component
public class Swagger2Config {

    private final SwaggerModuleConfiguration moduleConfiguration;

    @Autowired
    public Swagger2Config(SwaggerModuleConfiguration moduleConfiguration) {
        this.moduleConfiguration = moduleConfiguration;
        autoConfig();
    }


    public void autoConfig() {
        List<SwaggerModule> moduleList = moduleConfiguration.getModules();
        moduleList.forEach(module -> {
            Docket docket = SwaggerUtil.createDocket(module);
            String groupKey = module.getGroupKey();
            SpringUtil.registerBean(groupKey, docket);
        });
    }
}
