package org.jeeasy.common.core.annotation.dict;

import org.springframework.core.annotation.AliasFor;

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

    @AliasFor("dictCode")
    String value() default "";
    /**
     * 方法描述：这是返回后Put到josn中的文本key值
     *
     * @return 返回类型： String
     */
    @AliasFor("value")
    String dictCode() default "";


    /**
     * 当 type = ENUM 时，enum之前的class
     * @return {@link Class}
     */
    Class<? extends Enum> dictEnum() default Enum.class;

}
