package org.jeeasy.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author AlpsDDJ
 * @date 2020/11/23
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Dict {
    /**
     * 方法描述：这是返回后Put到josn中的文本key值
     *
     * @return 返回类型： String
     */
    String dictCode() default "";

    /**
     * 字典类型
     * @return {@link Dict.DictType}
     */
    DictType dictType() default DictType.DICT;

    /**
     * 当 type = ENUM 时，enum之前的class
     * @return {@link Class}
     */
    Class<? extends Enum> dictEnum() default Enum.class;

    enum DictType {
        // 数据字典表
        DICT,
        // 其他数据表
        TABLE,
        // enum
        ENUM,
        ;
    }
}
