package org.jeeasy.common.core.doc.tools;

import io.swagger.annotations.ApiOperation;
import org.jeeasy.common.core.constant.CommonConstant;
import org.jeeasy.common.core.doc.config.SwaggerModule;
import org.jeeasy.common.core.tools.Tools;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author yw
 */
public class SwaggerUtil {

    public static Docket createDocket(SwaggerModule module) {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName(module.getGroupName())
                .apiInfo(SwaggerUtil.apiInfo(module.getTitle(), module.getVersion(), module.getDescription(), module.getContact()))
                .select()
                //此包路径下的类，才生成接口文档
                .apis(RequestHandlerSelectors.basePackage(module.getBasePackage()))
                //加了ApiOperation注解的类，才生成接口文档
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(Tools.isEmpty(module.getPaths()) ? PathSelectors.any() : PathSelectors.regex(module.getPaths()))
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(securityContexts());
    }

    /**
     * 创建 Docket
     *
     * @return
     */
//    Docket createApiDocket();

    /**
     * 生成 apiInfo
     *
     * @param title       api文档标题
     * @param version     api版本
     * @param description api描述
     * @param contact     作者
     * @return ApiInfo
     */
    public static ApiInfo apiInfo(String title, String version, String description, String contact) {
        return new ApiInfoBuilder()
                // //大标题
                .title(title)
                // 版本号
                .version(version)
//				.termsOfServiceUrl("NO terms of service")
                // 描述
                .description(description)
                // 作者
                .contact(contact)
                .license("The Apache License, Version 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .build();
    }

    /**
     * 新增 securityContexts 保持登录状态
     */
    public static List<SecurityContext> securityContexts() {
        return new ArrayList(
                Collections.singleton(SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build())
        );
    }

    public static List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return new ArrayList(Collections.singleton(new SecurityReference(CommonConstant.X_ACCESS_TOKEN, authorizationScopes)));
    }

    /***
     * oauth2配置
     * 需要增加swagger授权回调地址
     * http://localhost:8888/webjars/springfox-swagger-ui/o2c.html
     * @return
     */
    public static SecurityScheme securityScheme() {
        return new ApiKey(CommonConstant.X_ACCESS_TOKEN, CommonConstant.X_ACCESS_TOKEN, "header");
    }

    /**
     * JWT token
     *
     * @return
     */
    public static List<Parameter> setHeaderToken() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name(CommonConstant.X_ACCESS_TOKEN).description("token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return pars;
    }
}
