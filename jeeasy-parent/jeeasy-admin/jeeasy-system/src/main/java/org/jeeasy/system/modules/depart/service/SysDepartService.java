package org.jeeasy.system.modules.depart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.depart.entity.SysDepart;

/**
 * @author AlpsDDJ
 * @date 2020/11/24 11:40
 */
public interface SysDepartService extends IService<SysDepart> {

    /**
     * 保存部门数据
     * @param depart
     */
    void saveDepartData(SysDepart depart);

    /**
     * 获取新的子部门编码
     * @param parentId 父级部门id
     * @return 新子部门编码
     */
    SysDepart getNewSubDepartParams(String parentId);
}
