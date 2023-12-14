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
public enum IndexTypeEnum implements IDictEnum<String> {
    NORMAL("normal"),
    UNIQUE("unique");

    private final String value;

    @Override
    public String getText() {
        return this.value;
    }
}
