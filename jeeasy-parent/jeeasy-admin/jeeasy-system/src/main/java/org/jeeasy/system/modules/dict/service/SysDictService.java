package org.jeeasy.system.modules.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.common.core.domain.vo.DictVo;
import org.jeeasy.system.modules.dict.domain.SysDict;
import org.jeeasy.system.modules.dict.domain.SysTableDict;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2021/8/13 10:01
 */
@Service
public interface SysDictService  extends IService<SysDict> {

    /**
     * 根据上级 code 获取字典列表
     * @param parentCode
     * @return
     */
    List<SysDict> queryByParentCode(String parentCode);

    /**
     * 根据 TableDict 获取字典列表
     * @param parentCode
     * @return
     */
    List<DictVo> queryByTableDict(SysTableDict tableDict);
}
