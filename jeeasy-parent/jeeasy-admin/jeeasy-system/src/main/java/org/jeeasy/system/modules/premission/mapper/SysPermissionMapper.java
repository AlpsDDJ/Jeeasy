package org.jeeasy.system.modules.premission.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.jeeasy.system.modules.premission.domain.SysPermission;

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
}
