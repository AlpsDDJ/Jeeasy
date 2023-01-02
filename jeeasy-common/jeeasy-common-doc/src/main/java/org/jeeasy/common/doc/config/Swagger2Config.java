package org.jeeasy.common.doc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @Author scott
 */
@Slf4j
@Component
public class Swagger2Config implements WebMvcConfigurer {

    @Bean
    @Lazy
    public OpenAPI springShopOpenApi() {
        final String loginToken = "BearerAuth";
        return new OpenAPI().info(new Info().title("Jeeasy API")
                        .description("Jeeasy")
                        .version("v1.0.0")).externalDocs(new ExternalDocumentation()
                        .description("Jeeasy")
                        .url("https://www.jeeasy.cn"))
                .components(new Components().addSecuritySchemes(loginToken, new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                        .in(SecurityScheme.In.HEADER)
                        .name(loginToken)))
                .addSecurityItem(new SecurityRequirement().addList(loginToken));
    }

//    @Autowired
//    public void autoConfig(SwaggerModuleConfiguration moduleConfiguration) {
//        List<SwaggerModule> moduleList = moduleConfiguration.getModules();
//        moduleList.forEach(module -> {
//            GroupedOpenApi docket = createApiGroup(module);
//            String groupKey = module.getGroupKey();
//            SpringUtil.registerBean(groupKey, docket);
//        });
//    }
//
//    public GroupedOpenApi createApiGroup(SwaggerModule module) {
//        return GroupedOpenApi.builder().group(module.getGroupKey())
//                .packagesToScan(module.getBasePackage())
//                .pathsToMatch(module.getPaths())
//                .displayName(module.getGroupName())
//                .build();
//    }


}
