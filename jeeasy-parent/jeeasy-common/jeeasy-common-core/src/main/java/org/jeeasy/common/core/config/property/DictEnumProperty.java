package org.jeeasy.common.core.config.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
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
@ConfigurationProperties(prefix = "jeeasy.dict")
public class DictEnumProperty {

    /**
     * 根据属性名称自动翻译字典，无需注释@Dict
     */
    private Map<String, Class<? extends Enum>> autoTranslateEnumClass = new HashMap<>();

    private String[] dictTableFlag = new String[] {"#"};

    private String dictTextSuffix = "_dictText";
}
