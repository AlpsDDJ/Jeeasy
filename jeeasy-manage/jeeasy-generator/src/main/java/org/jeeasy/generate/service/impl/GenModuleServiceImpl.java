package org.jeeasy.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.generate.domain.GenModule;
import org.jeeasy.generate.mapper.GenModuleMapper;
import org.jeeasy.generate.service.GenModuleService;
import org.springframework.stereotype.Service;

/**
 * @author AlpsDDJ
 * @date 2023/12/14
 */
@Service
public class GenModuleServiceImpl extends ServiceImpl<GenModuleMapper, GenModule> implements GenModuleService {
    @Override
    public GenModule getByCode(String code) {
        return baseMapper.selectOne(new QueryWrapper<GenModule>().lambda().eq(GenModule::getModuleCode, code));
    }
}
