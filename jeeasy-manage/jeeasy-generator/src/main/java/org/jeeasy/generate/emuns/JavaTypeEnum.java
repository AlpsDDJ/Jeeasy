package org.jeeasy.generate.emuns;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jeeasy.common.core.enums.IDictEnum;


@Getter
@AllArgsConstructor
public enum JavaTypeEnum implements IDictEnum<String> {
    STRING("String"),
    INTEGER("Integer"),
    LONG("Long"),
    DOUBLE("Double"),
    FLOAT("Float"),
    BOOLEAN("Boolean"),
    DATE("java.time.LocalDate"),
    TIME("java.time.LocalTime"),
    DATETIME("java.time.LocalDateTime"),
    CHAR("char"),
    BYTE("Byte"),
    SHORT("Short"),
    BIG_DECIMAL("java.math.BigDecimal"),
    BIG_INTEGER("java.math.BigInteger"),
    ;
    private final String value;

    public static void main(String[] args) {
    }

    @Override
    public String getText() {
        return this.value;
    }
}
