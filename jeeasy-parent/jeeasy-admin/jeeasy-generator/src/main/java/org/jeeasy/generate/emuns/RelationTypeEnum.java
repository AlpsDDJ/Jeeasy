package org.jeeasy.generate.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.DictEnum;

/**
 * @author AlpsDDJ
 * @date 2021/1/14 13:30
 */
@Getter
@AllArgsConstructor
public enum RelationTypeEnum implements DictEnum<String> {
    /**
     * 一对多
     */
    ONE_TO_MANY("oneToMany", "一对多"),
    
    /**
     * 一对一
     */
    ONE_TO_ONE("oneToOne", "一对一"),
    ;
    private final String value;
    private final String text;
}
