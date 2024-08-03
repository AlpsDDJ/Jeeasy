package org.jeeasy.system.modules.common.service.impl;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.jeeasy.common.core.config.property.DictEnumProperty;
import org.jeeasy.common.core.domain.vo.DictVo;
import org.jeeasy.common.core.enums.IDictEnum;
import org.jeeasy.common.core.service.CommonService;
import org.jeeasy.common.core.tools.DictUtil;
import org.jeeasy.common.core.tools.HttpUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.domain.SysTableDict;
import org.jeeasy.system.modules.dict.service.SysDictService;
import org.jeeasy.system.modules.dict.service.SysTableDictService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.dev33.satoken.SaManager.log;

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
    public List<DictVo> getDictsByCode(String code, String parentId, boolean async) {
        List<DictVo> dicts = new ArrayList<>();
        // 自定义表查询字典数据
        if (StrUtil.containsAny(code, dictEnumProperty.getDictTableFlag())) {
            String dictCode = StrUtil.replaceChars(code, dictEnumProperty.getDictTableFlag(), StrUtil.EMPTY);
            SysTableDict sysTableDict = tableDictService.getByDictCode(dictCode);
            if (Tools.isNotEmpty(sysTableDict)) {
                if (Tools.isNotEmpty(parentId)) {
                    sysTableDict.setParentValue(parentId);
                }
                Map<String, Object> reqParams = HttpUtil.getReqParams();
                sysTableDict.replaceParams(reqParams);
                log.debug("tableName: {}", sysTableDict.getTableName());
                List<? extends DictVo> sysDicts = dictService.queryByTableDict(sysTableDict, async);
                dicts.addAll(sysDicts);
//                sysDicts.forEach(dict -> {
//                    dicts.add(new DictVo().setDictCode(dict.getDictCode()).setDictName(dict.getDictName()));
//                });
            }
        } else {
            // 枚举类型字典数据
            List<IDictEnum<?>> dictEnum = DictUtil.getDictEnum(code);
            if (Tools.isNotEmpty(dictEnum)) {
                dictEnum.forEach(de -> {
                    dicts.add(new DictVo(de));
                });
            } else {
                List<SysDict> sysDicts = dictService.queryByParentCode(Tools.isEmpty(parentId) ? code : parentId);
                sysDicts.forEach(dict -> {
                    dicts.add(new DictVo().setDictCode(dict.getDictCode()).setDictName(dict.getDictName()));
                });
            }
        }
        return dicts;
    }

    @Override
    public List<DictVo> getDictParents(String code, String value) {

        String dictCode = StrUtil.replaceChars(code, dictEnumProperty.getDictTableFlag(), StrUtil.EMPTY);
        SysTableDict sysTableDict = tableDictService.getByDictCode(dictCode);
        if (Tools.isNotEmpty(sysTableDict)) {
//            List<DictVo> sysDicts = dictService.queryByTableDict(sysTableDict);
//            dicts.addAll(sysDicts);
//                sysDicts.forEach(dict -> {
//                    dicts.add(new DictVo().setDictCode(dict.getDictCode()).setDictName(dict.getDictName()));
//                });
        }

        return null;
    }


}
