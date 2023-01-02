package org.jeeasy.system.modules.premission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.jeeasy.system.modules.premission.domain.SysPermission;
import org.jeeasy.system.modules.premission.domain.vo.MenuVo;

import java.util.List;

/**
 * 菜单权限表(sys_permission)数据Mapper
 *
 * @author AlpsDDJ
 * @since 2020-11-21 13:52:05
 * @description 菜单权限
*/
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

//    @ResultMap("PermissionTreeMap")
//    List<SysPermission> queryTreeList();

    /**
     * 通过角色id查询权限
     *
     * @param roleId 角色id
     * @return {@link List}<{@link SysPermission}>
     */
    List<SysPermission> queryByRoleId(@Param("roleId") String roleId);

    /**
     * 通过用户id查询权限
     *
     * @param userId 用户id
     * @return {@link List}<{@link SysPermission}>
     */
    List<SysPermission> queryByUserId(@Param("userId") String userId);

    /**
     * 通过用户id查询菜单
     *
     * @param userId 用户id
     * @return {@link List}<{@link MenuVo}>
     */
    List<MenuVo> queryMenuByUserId(@Param("userId") String userId, @Param("parentId") String parentId);
}
