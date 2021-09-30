package org.jeeasy.common.core.service;

import cn.hutool.core.util.EnumUtil;
import org.jeeasy.common.core.domain.vo.TableDictVo;
import org.jeeasy.common.core.enums.IDictEnum;
import org.jeeasy.common.core.tools.Tools;

/**
 * @author AlpsDDJ
 * @date 2020/11/23 9:43
 */
public interface DictTranslationService {

    /**
     * 根据enum翻译字典
     *
     * @param enumClass
     * @param value
     * @return
     */
    default <T> String translateDictFromEnum(Class<? extends Enum> enumClass, T value) {
        if (EnumUtil.isEnum(enumClass)) {
            Enum enableFlagEnum = EnumUtil.likeValueOf(enumClass, value);
            IDictEnum<T> dictEnum = (IDictEnum<T>) enableFlagEnum;
            if (Tools.isNotEmpty(dictEnum)) {
                return dictEnum.getText();
            }
        }
        return null;
    }

    /**
     * 根据 code 获取 TableDict
     * @param code
     * @return
     */
    TableDictVo getTableDictByCode(String code);

    /**
     * 根据table翻译字典
     *
     * @param code
     * @param value
     * @return
     */
    String translateDictFromTable(TableDictVo tableDict, Object value);

    /**
     * 普通字典的翻译
     *
     * @param code
     * @param value
     * @return
     */
    String translateDict(String code, Object value);
}
