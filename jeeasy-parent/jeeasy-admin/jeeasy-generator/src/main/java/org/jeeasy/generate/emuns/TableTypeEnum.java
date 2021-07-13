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
public enum TableTypeEnum implements DictEnum<String> {
    SINGLE("single", "单表"),
    MAIN("main", "主表"),
    SLAVE("slave", "附表"),
    CATALOG("catalog", "分类"),
    ;
    private final String value;
    private final String text;
}
