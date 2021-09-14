package org.jeeasy.common.core.annotation;

import java.lang.annotation.*;

/**
 * 标记需要翻译字典的方法
 *
 * @author AlpsDDJ
 * @date 2020/11/23 9:31
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DictTranslation {
}
