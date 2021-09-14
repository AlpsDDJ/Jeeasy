package org.jeeasy.common.doc.config;

import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Annotation;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Getter
@Setter
@NoArgsConstructor
public class SwaggerModule {
    private String groupName;
    private String groupKey;
    private String title = "API文档";
    private String version = "1.0";
    private String description;
    private String contact = "AlpsDDJ";
    private String basePackage = "org.jeeasy";
    private String paths;
    private Class<? extends Annotation> annotation = ApiOperation.class;

}
