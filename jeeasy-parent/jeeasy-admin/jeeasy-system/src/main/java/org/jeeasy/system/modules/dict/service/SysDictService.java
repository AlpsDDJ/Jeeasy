package org.jeeasy.system.modules.dict.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.dict.entity.SysDict;

import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2021/8/13 10:01
 */
public interface SysDictService  extends IService<SysDict> {

    /**
     * 根据上级code获取字典列表
     * @param parentCode
     * @return
     */
    List<SysDict> queryByParentCode(String parentCode);
}
