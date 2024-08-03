package org.jeeasy.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.generate.domain.GenTemplate;

/**
 * 代码生成模板 Service
 *
 * @author wei.yang
 * @date 2023-12-17 14:56:27
 */
public interface GenTemplateService extends IService<GenTemplate> {
    GenTemplate getByTypeAndNFileName(String type, String fileName);
}