package org.jeeasy.common.core.service;


import org.jeeasy.common.core.domain.vo.DictVo;

import java.util.List;

/**
 *
 * @author mobie
 */
public interface CommonService {

    /**
     * 根据dictCode获取字典列表
     * @param code
     * @return
     */
    List<DictVo> getDictsByCode(String code);

}
