package org.jeeasy.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.generate.domain.GenModule;

/**
 * @author AlpsDDJ
 * @date 2023/12/14
 */
public interface GenModuleService extends IService<GenModule> {
    GenModule getByCode(String code);
}
