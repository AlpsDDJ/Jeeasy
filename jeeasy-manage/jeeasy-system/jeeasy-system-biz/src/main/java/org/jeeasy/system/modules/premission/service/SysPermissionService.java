package org.jeeasy.system.modules.premission.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.system.modules.premission.domain.SysPermission;
import org.jeeasy.system.modules.premission.domain.vo.MenuVo;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 查询所有的子菜单
     *
     * @param parentId 父id
     * @return {@link List}<{@link SysPermission}>
     */
    IPage<SysPermission> queryPageTreeList(String parentId, QueryPageModel query, HttpServletRequest req);

    /**
     * 通过角色id查询权限
     *
     * @param roleId 角色id
     * @return {@link List}<{@link SysPermission}>
     */
    List<SysPermission> queryByRoleId(String roleId);

    /**
     * 通过用户id查询权限
     *
     * @param userId 用户id
     * @return {@link List}<{@link SysPermission}>
     */
    List<SysPermission> queryByUserId(String userId);

    /**
     * 通过用户id查询菜单
     *
     * @param userId 用户id
     * @return {@link List}<{@link MenuVo}>
     */
    List<MenuVo> queryMenuByUserId(String userId);

}
