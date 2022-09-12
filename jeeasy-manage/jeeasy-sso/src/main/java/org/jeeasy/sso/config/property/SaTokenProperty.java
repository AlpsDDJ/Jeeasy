package org.jeeasy.sso.config.property;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * DictEnumProperty: 根据Enum翻译字典 配置
 *
 * @author AlpsDDJ
 * @version v1.0
 * @date 2020/11/23 20:09
 */
@Setter
@Getter
@ConfigurationProperties(prefix = "sa-token.path")
public class SaTokenProperty {
    private String base = "/**";
    private List<String> exclude = new ArrayList<>();
    private List<MatchRule> rules;

    @Data
    public static class MatchRule {
        private String path;
//        private List<String> exclude = new ArrayList<>();
        private List<String> roles = new ArrayList<>();
        private List<String> permissions = new ArrayList<>();
    }
}
