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
public @interface DictEnum {
    /**
     * 方法描述：这是返回后Put到josn中的文本key值
     *
     * @return 返回类型： String
     */
    String value() default "";

}
