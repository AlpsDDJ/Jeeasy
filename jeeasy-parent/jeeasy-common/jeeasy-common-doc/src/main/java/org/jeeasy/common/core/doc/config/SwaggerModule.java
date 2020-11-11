package org.jeeasy.common.core.doc.config;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Getter
@Setter
@NoArgsConstructor
public class SwaggerModule {
    private String groupName;
    private String title = "API文档";
    private String version = "1.0";
    private String description;
    private String contact = "AlpsDDJ";
    private String basePackage = "org.jeeasy";
    private String paths;
    private Class annotation = ApiOperation.class;

    public SwaggerModule(String groupName, String title, String version, String basePackage, Class annotation) {
        this.groupName = groupName;
        this.title = title;
        this.version = version;
        this.basePackage = basePackage;
        this.annotation = annotation;
    }
}
