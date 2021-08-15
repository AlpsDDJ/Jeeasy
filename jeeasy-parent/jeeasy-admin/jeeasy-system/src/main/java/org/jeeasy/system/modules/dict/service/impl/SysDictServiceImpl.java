package org.jeeasy.system.modules.dict.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.system.modules.dict.domain.SysDict;
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
}
