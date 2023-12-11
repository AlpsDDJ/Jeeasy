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
public enum JdbcTypeEnum implements IDictEnum<String> {
    VARCHAR("varchar"),
    DATE("date"),
    DATETIME("datetime"),
    INT("int"),
    TINYINT("tinyint"),
    BIGINT("bigint"),
    FLOAT("float"),
    DOUBLE("double"),
    DECIMAL("decimal"),
    TEXT("text"),
    BLOB("blob"),
    ;

    private final String value;

    @Override
    public String getText() {
        return this.value;
    }

}
