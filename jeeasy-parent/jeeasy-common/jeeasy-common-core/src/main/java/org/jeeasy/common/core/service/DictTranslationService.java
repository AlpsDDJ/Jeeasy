package org.jeeasy.common.core.service;

import cn.hutool.core.util.EnumUtil;
import org.jeeasy.common.core.enums.IDictEnum;
import org.jeeasy.common.core.tools.Tools;

/**
 * @author AlpsDDJ
 * @date 2020/11/23 9:43
 */
public interface DictTranslationService {
    /**
     * 根据table翻译字典
     *
     * @param code
     * @param key
     * @return
     */
    String translateDictFromTable(String code, Object key);

    /**
     * 根据enum翻译字典
     *
     * @param enumClass
     * @param key
     * @return
     */
    default <T> String translateDictFromEnum(Class<? extends Enum> enumClass, T key) {
        if (EnumUtil.isEnum(enumClass)) {
            Enum enableFlagEnum = EnumUtil.likeValueOf(enumClass, key);
            IDictEnum<T> dictEnum = (IDictEnum<T>) enableFlagEnum;
            if (Tools.isNotEmpty(dictEnum)) {
                return dictEnum.getText();
            }
        }
        return null;
    }

    /**
     * 普通字典的翻译
     *
     * @param code
     * @param key
     * @return
     */
    String translateDict(String code, Object key);
}
