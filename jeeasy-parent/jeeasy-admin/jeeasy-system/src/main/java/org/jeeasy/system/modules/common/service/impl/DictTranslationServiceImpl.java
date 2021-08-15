package org.jeeasy.system.modules.common.service.impl;

import org.jeeasy.common.db.service.DictTranslationService;
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
    public String translateDictFromTable(String code, Object key) {
        SysTableDict tableDict = tableDictMapper.getByDictCode(code);
        return null;
    }

    @Override
    public String translateDict(String code, Object key) {
        SysDict dict = dictMapper.getOneByParentCodeAndDictCode(code, key.toString());
        return dict.getDictName();
    }
}
