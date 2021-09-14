package org.jeeasy.system.modules.dict.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.system.modules.dict.domain.SysTableDict;
import org.jeeasy.system.modules.dict.mapper.SysTableDictMapper;
import org.jeeasy.system.modules.dict.service.SysTableDictService;
import org.springframework.stereotype.Service;

/**
 *
 * @author mobie
 */
@Service
public class SysTableDictServiceImpl extends ServiceImpl<SysTableDictMapper, SysTableDict> implements SysTableDictService {

    @Override
    public SysTableDict getByDictCode(String dictCode) {
        return baseMapper.getByDictCode(dictCode);
    }
}




