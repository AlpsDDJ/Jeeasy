package org.jeeasy.system.modules.dict.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.common.core.domain.vo.DictVo;
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
    public List<DictVo> queryByTableDict(SysTableDict tableDict) {
        String tableName = tableDict.getTableName();
        if(StrUtil.contains(tableName, "select")){
            tableDict.setTableName(StrUtil.concat(true, "(", tableName, ")"));
        }
        return baseMapper.queryByTableDict(tableDict);
    }
}
