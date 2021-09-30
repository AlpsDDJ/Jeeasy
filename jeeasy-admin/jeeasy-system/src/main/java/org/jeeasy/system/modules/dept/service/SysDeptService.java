package org.jeeasy.system.modules.dept.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.dept.domain.SysDept;

/**
 * @author AlpsDDJ
 * @date 2020/11/24 11:40
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 保存部门数据
     * @param dept
     */
    void saveDeptData(SysDept dept);

    /**
     * 获取新的子部门编码
     * @param parentId 父级部门id
     * @return 新子部门编码
     */
    SysDept getNewSubDeptParams(String parentId);
}
