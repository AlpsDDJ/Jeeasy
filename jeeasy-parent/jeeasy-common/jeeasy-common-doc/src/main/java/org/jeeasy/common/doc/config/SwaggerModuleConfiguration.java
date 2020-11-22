package org.jeeasy.common.doc.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jeeasy.swagger")
public class SwaggerModuleConfiguration {
    private boolean enable = false;
    private Map<String, SwaggerModule> module = new HashMap<>();
}
