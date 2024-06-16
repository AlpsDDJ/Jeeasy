package org.jeeasy.common.core.annotation.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.tangzc.mpe.autotable.annotation.ColumnComment;
import com.tangzc.mpe.autotable.annotation.ColumnDefault;
import com.tangzc.mpe.autotable.annotation.ColumnType;
import com.tangzc.mpe.autotable.annotation.NotNull;
import com.tangzc.mpe.autotable.annotation.enums.DefaultValueEnum;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TableField
@ColumnType
@NotNull
@ColumnDefault
@ColumnComment("")
//@Schema
public @interface JeeasyField {
    @AliasFor(
            annotation = TableField.class,
            attribute = "value"
    )
    String value() default "";

    @AliasFor(
            annotation = ColumnType.class,
            attribute = "value"
    )
    String type() default "";

    @AliasFor(
            annotation = ColumnType.class,
            attribute = "length"
    )
    int length() default -1;

    @AliasFor(
            annotation = ColumnType.class,
            attribute = "decimalLength"
    )
    int decimalLength() default -1;

    @AliasFor(
            annotation = NotNull.class,
            attribute = "value"
    )
    boolean notNull() default false;

    @AliasFor(
            annotation = ColumnDefault.class,
            attribute = "type"
    )
    DefaultValueEnum defaultValueType() default DefaultValueEnum.UNDEFINED;

    @AliasFor(
            annotation = ColumnDefault.class,
            attribute = "value"
    )
    String defaultValue() default "";

    @AliasFor(
            annotation = ColumnComment.class,
            attribute = "value"
    )
    String comment() default "";
}
