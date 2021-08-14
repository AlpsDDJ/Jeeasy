package org.jeeasy.system.modules.common.service;

import org.jeeasy.system.modules.common.vo.DictVo;

import java.util.List;

/**
 *
 * @author mobie
 */
public interface SystemCommonService {

    /**
     * 根据dictCode获取字典列表
     * @param code
     * @return
     */
    List<DictVo> getDictsByCode(String code);

}
