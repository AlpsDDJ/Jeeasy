package org.jeeasy.common.doc.config;

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

    public SwaggerModule(String groupName, String title, String version, String basePackage) {
        this.groupName = groupName;
        this.title = title;
        this.version = version;
        this.basePackage = basePackage;
    }
}
