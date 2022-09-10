package org.jeeasy.system.modules.dept.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.dept.domain.SysDept;
import org.jeeasy.system.modules.premission.domain.SysPermission;

import java.util.List;

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

    /**
     * 查询所有的子菜单
     *
     * @param parentId 父id
     * @return {@link List}<{@link SysPermission}>
     */
    List<SysDept> queryAllChildren(String parentId);
}
