package org.jeeasy.generate.emuns;

import com.tangzc.mpe.autotable.strategy.mysql.data.MysqlTypeConstant;
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
    VARCHAR(MysqlTypeConstant.VARCHAR),
    BIT(MysqlTypeConstant.BIT),
    INT(MysqlTypeConstant.INT),
    TINYINT(MysqlTypeConstant.TINYINT),
    BIGINT(MysqlTypeConstant.BIGINT),
    FLOAT(MysqlTypeConstant.FLOAT),
    DOUBLE(MysqlTypeConstant.DOUBLE),
    DECIMAL(MysqlTypeConstant.DECIMAL),
    DATE(MysqlTypeConstant.DATE),
    TIME(MysqlTypeConstant.TIME),
    DATETIME(MysqlTypeConstant.DATETIME),
    TIMESTAMP(MysqlTypeConstant.TIMESTAMP),
    TEXT(MysqlTypeConstant.TEXT),
    LONGTEXT(MysqlTypeConstant.LONGTEXT),
    MEDIUMTEXT(MysqlTypeConstant.MEDIUMTEXT),
    BLOB(MysqlTypeConstant.BLOB),
    MEDIUMBLOB(MysqlTypeConstant.MEDIUMBLOB),
    LONGBLOB(MysqlTypeConstant.LONGBLOB),
    ;

    private final String value;

    //private final JdbcType jdbcType;

    //public static JdbcTypeEnum getJdbcType(String value) {
    //
    //    for (JdbcTypeEnum jdbcTypeEnum : JdbcTypeEnum.values()) {
    //        if (jdbcTypeEnum.value.equals(value)) {
    //            return jdbcTypeEnum;
    //        }
    //    }
    //    throw new JeeasyException("未知数据类型: " + value);
    //}
    //
    //public static void main(String[] args) {
    //    JdbcTypeEnum bit = JdbcTypeEnum.getJdbcType("datetime");
    //    System.out.println(bit.name());
    //}

    @Override
    public String getText() {
        return this.value;
    }

}
