package org.jeeasy.system.modules.dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeeasy.system.modules.dict.domain.SysTableDict;

/**
 * @author mobie
 * @Entity org.jeeasy.system.modules.dict.domain.SysTableDict
 */
public interface SysTableDictMapper extends BaseMapper<SysTableDict> {

    /**
     * queryByDictCode
     * @param dictCode
     * @return
     */
    SysTableDict getByDictCode(@Param("dictCode") String dictCode);
}




