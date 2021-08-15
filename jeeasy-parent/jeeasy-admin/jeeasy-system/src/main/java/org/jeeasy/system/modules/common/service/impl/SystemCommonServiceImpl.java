package org.jeeasy.system.modules.common.service.impl;

import cn.hutool.core.util.StrUtil;
import org.jeeasy.common.core.enums.IDictEnum;
import org.jeeasy.common.core.service.CommonService;
import org.jeeasy.common.core.tools.DictUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.DictVo;
import org.jeeasy.common.db.config.property.DictEnumProperty;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.domain.SysTableDict;
import org.jeeasy.system.modules.dict.service.SysDictService;
import org.jeeasy.system.modules.dict.service.SysTableDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mobie
 */
@Service
public class SystemCommonServiceImpl implements CommonService {


    @Resource
    private DictEnumProperty dictEnumProperty;
    @Resource
    private SysDictService dictService;
    @Resource
    private SysTableDictService tableDictService;

    @Override
    public List<DictVo> getDictsByCode(String code) {
        List<DictVo> dicts = new ArrayList<>();
        if (StrUtil.containsAny(code, dictEnumProperty.getDictTableFlag())) {
            String dictCode = StrUtil.replaceChars(code, dictEnumProperty.getDictTableFlag(), StrUtil.EMPTY);
            SysTableDict sysTableDict = tableDictService.getByDictCode(dictCode);
            if(Tools.isNotEmpty(sysTableDict)){
                List<SysDict> sysDicts = dictService.queryByTableDict(sysTableDict);
                sysDicts.forEach(dict -> {
                    dicts.add(new DictVo().setDictCode(dict.getDictCode()).setDictName(dict.getDictName()));
                });
            }

        } else {
            List<IDictEnum<?>> dictEnum = DictUtil.getDictEnum(code);
            if(Tools.isNotEmpty(dictEnum)){
                dictEnum.forEach(de -> {
                    dicts.add(new DictVo(de));
                });
            } else {
                List<SysDict> sysDicts = dictService.queryByParentCode(code);
                sysDicts.forEach(dict -> {
                    dicts.add(new DictVo().setDictCode(dict.getDictCode()).setDictName(dict.getDictName()));
                });
            }
        }
        return dicts;
    }



}
