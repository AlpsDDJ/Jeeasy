package org.jeeasy.system.modules.common.service.impl;

import jakarta.annotation.Resource;
import org.jeeasy.common.core.domain.dto.TranslateDictDTO;
import org.jeeasy.common.core.domain.dto.TranslateDictFromTableDTO;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.domain.vo.TableDictVo;
import org.jeeasy.common.core.service.IDictTranslationService;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.domain.SysTableDict;
import org.jeeasy.system.modules.dict.mapper.SysDictMapper;
import org.jeeasy.system.modules.dict.mapper.SysTableDictMapper;
import org.springframework.stereotype.Component;


/**
 * @author AlpsDDJ
 * @date 2020/11/23 13:14
 */
@Component
public class DictTranslationServiceImpl implements IDictTranslationService {

    @Resource
    SysDictMapper dictMapper;
    @Resource
    SysTableDictMapper tableDictMapper;


    @Override
    public R<TableDictVo> getTableDictByCode(String code) {
        SysTableDict tableDict = tableDictMapper.getByDictCode(code);
        if (Tools.isEmpty(tableDict)) {
            return null;
        }
        TableDictVo dictVo = new TableDictVo()
                .setTableName(tableDict.getTableName())
                .setDatabaseName(tableDict.getDatabaseName())
                .setNameColumn(tableDict.getNameColumn())
                .setValueColumn(tableDict.getValueColumn());
        return R.ok(dictVo);
    }

    @Override
    public R<String> translateDictFromTable(TranslateDictFromTableDTO dto) {
        SysDict dict = dictMapper.getOneByTableDictAndCode(dto.getTableDict(), dto.getValue().toString());
        if (Tools.isNotEmpty(dict)) {
            return R.ok(dict.getDictName());
        }
        return R.ok("");
    }

    @Override
    public R<String> translateDict(TranslateDictDTO dto) {
        SysDict dict = dictMapper.getOneByParentCodeAndDictCode(dto.getCode(), dto.getValue().toString());
        if (Tools.isNotEmpty(dict)) {
            return R.ok(dict.getDictName());
        }
        return R.ok("");
    }
}
