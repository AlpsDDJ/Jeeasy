package org.jeeasy.common.core.enums;

/**
 * @author AlpsDDJ
 * @date 2020/11/23 10:58
 */
public interface IDictEnum<T> {

    /**
     * 获取字典项文本
     * @return
     */
    T getValue();

    /**
     * 获取字典项文本
     * @return
     */
    String getText();

//    default String getDictCode(){
//        String className = this.getClass().getSimpleName();
//
//        return StrUtil.toCamelCase(className.replace("Enum", ""));
//    }

}
