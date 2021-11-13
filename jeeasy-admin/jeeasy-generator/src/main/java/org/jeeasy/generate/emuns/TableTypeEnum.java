package org.jeeasy.generate.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.IDictEnum;

/**
 * 表类型
 *
 * @author AlpsDDJ
 * @date 2021/1/14 13:30
 */
@Getter
@AllArgsConstructor
public enum TableTypeEnum implements IDictEnum<String> {
    /**
     * 单表
     */
    SINGLE("single", "单表"),
    /**
     * 主表
     */
    MAIN("main", "主表"),
    /**
     * 附表
     */
    SLAVE("slave", "附表"),
    /**
     * 分类
     */
    CATALOG("catalog", "分类");

    private final String value;
    private final String text;
}
