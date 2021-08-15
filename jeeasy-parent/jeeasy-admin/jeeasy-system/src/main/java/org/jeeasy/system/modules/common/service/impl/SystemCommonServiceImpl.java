package org.jeeasy.system.modules.common.service.impl;

import cn.hutool.core.util.StrUtil;
import org.jeeasy.common.core.enums.IDictEnum;
import org.jeeasy.common.core.service.CommonService;
import org.jeeasy.common.core.tools.DictUtil;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.DictVo;
import org.jeeasy.common.db.config.property.DictEnumProperty;
import org.jeeasy.system.modules.dict.service.SysDictService;
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

    @Override
    public List<DictVo> getDictsByCode(String code) {
        List<DictVo> dicts = new ArrayList<>();
        if (StrUtil.containsAny(code, dictEnumProperty.getDictTableFlag())) {
            String tableKey = StrUtil.replaceChars(code, dictEnumProperty.getDictTableFlag(), StrUtil.EMPTY);




        } else {
            List<IDictEnum<?>> dictEnum = DictUtil.getDictEnum(code);
            if(Tools.isNotEmpty(dictEnum)){
                dictEnum.forEach(de -> {
                    dicts.add(new DictVo(de));
                });
            } else {

            }
        }
        return dicts;
    }



}
