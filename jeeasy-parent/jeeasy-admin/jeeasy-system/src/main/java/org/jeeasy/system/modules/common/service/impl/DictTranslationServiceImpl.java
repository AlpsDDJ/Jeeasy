package org.jeeasy.system.modules.common.service.impl;

import org.jeeasy.common.core.domain.TableDictVo;
import org.jeeasy.common.core.service.DictTranslationService;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.domain.SysTableDict;
import org.jeeasy.system.modules.dict.mapper.SysDictMapper;
import org.jeeasy.system.modules.dict.mapper.SysTableDictMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author AlpsDDJ
 * @date 2020/11/23 13:14
 */
@Component
public class DictTranslationServiceImpl implements DictTranslationService {

    @Resource
    SysDictMapper dictMapper;
    @Resource
    SysTableDictMapper tableDictMapper;


    @Override
    public TableDictVo getTableDictByCode(String code) {
        SysTableDict tableDict = tableDictMapper.getByDictCode(code);
        if(Tools.isEmpty(tableDict)){
            return null;
        }
        return new TableDictVo()
                .setTableName(tableDict.getTableName())
                .setDatabaseName(tableDict.getDatabaseName())
                .setNameColumn(tableDict.getNameColumn())
                .setValueColumn(tableDict.getValueColumn());
    }

    @Override
    public String translateDictFromTable(TableDictVo tableDict, Object value) {
        SysDict dict = dictMapper.getOneByTableDictAndCode(tableDict, value.toString());
        if(Tools.isNotEmpty(dict)){
            return dict.getDictName();
        }
        return "";
    }

    @Override
    public String translateDict(String code, Object value) {
        SysDict dict = dictMapper.getOneByParentCodeAndDictCode(code, value.toString());
        if(Tools.isNotEmpty(dict)){
            return dict.getDictName();
        }
        return "";
    }
}
