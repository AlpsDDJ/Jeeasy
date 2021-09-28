package org.jeeasy.system.modules.premission.service;


import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.premission.domain.SysPermission;

import java.util.List;

/**
 * 菜单权限表服务接口
 *
 * @author AlpsDDJ
 * @since 2020-11-21 13:52:05
 * @description 菜单权限
 */
public interface SysPermissionService extends IService<SysPermission> {

//    List<SysPermission> queryTreeList();

    /**
     * 查询所有的子菜单
     *
     * @param parentId 父id
     * @return {@link List}<{@link SysPermission}>
     */
    List<SysPermission> queryAllChildren(String parentId);

}
