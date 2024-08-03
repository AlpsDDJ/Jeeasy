package org.jeeasy.generate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.jeeasy.generate.domain.GenTemplate;
import org.jeeasy.generate.mapper.GenTemplateMapper;
import org.jeeasy.generate.service.GenTemplateService;
import org.springframework.stereotype.Service;

/**
 * 代码生成模板 ServiceImpl
 *
 * @author wei.yang
 * @date 2023-12-17 14:56:27
 */
@Service
public class GenTemplateServiceImpl extends ServiceImpl<GenTemplateMapper, GenTemplate> implements GenTemplateService {

    @Override
    public GenTemplate getByTypeAndNFileName(String type, String fileName) {
        QueryWrapper<GenTemplate> wrapper = QueryGenerator.ofWrapper();
        wrapper.lambda().eq(GenTemplate::getType, type).eq(GenTemplate::getFileName, fileName);
        return baseMapper.selectOne(wrapper);
    }
}
