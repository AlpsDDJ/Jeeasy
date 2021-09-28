package org.jeeasy.system.modules.dict.service.impl;

import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.common.core.domain.vo.DictVo;
import org.jeeasy.common.core.domain.vo.TreeDictVo;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.domain.SysTableDict;
import org.jeeasy.system.modules.dict.mapper.SysDictMapper;
import org.jeeasy.system.modules.dict.service.SysDictService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Override
    public List<SysDict> queryByParentCode(String parentCode) {
        return baseMapper.queryByParentCode(parentCode);
    }

    @Override
    public List<? extends DictVo> queryByTableDict(SysTableDict tableDict, boolean async) {
        String tableName = tableDict.getTableName();
        if (StrUtil.contains(tableName, "select")) {
            tableDict.setTableName(StrUtil.concat(true, "(", tableName, ")"));
        }
        if (BooleanEnum.yes(tableDict.getIsTree())) {
//            List<TreeDictVo> treeDictVos = baseMapper.queryTreeByTableDict(tableDict, async);
            return baseMapper.queryTreeByTableDict(tableDict, async);
        }

        return baseMapper.queryByTableDict(tableDict);
    }

    @Override
    public List<DictVo> getParent(SysTableDict tableDict, String value) {



        return null;
    }
}
