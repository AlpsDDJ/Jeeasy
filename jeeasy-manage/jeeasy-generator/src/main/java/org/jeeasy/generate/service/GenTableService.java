package org.jeeasy.generate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.generate.domain.GenTable;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public interface GenTableService extends IService<GenTable> {
    boolean updateWithFields(GenTable entity);

    boolean saveWithFields(GenTable entity);
}
