package org.jeeasy.system.modules.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.dict.domain.SysTableDict;

/**
 *
 * @author mobie
 */
public interface SysTableDictService extends IService<SysTableDict> {

    /**
     * 查询 tableDict配置
     * @param key
     * @return
     */
    SysTableDict getByDictCode(String dictCode);
}
