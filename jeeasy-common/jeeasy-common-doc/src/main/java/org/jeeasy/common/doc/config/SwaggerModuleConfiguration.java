package org.jeeasy.common.doc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jeeasy.doc")
public class SwaggerModuleConfiguration {
    private List<SwaggerModule> modules = new ArrayList<>();
}
