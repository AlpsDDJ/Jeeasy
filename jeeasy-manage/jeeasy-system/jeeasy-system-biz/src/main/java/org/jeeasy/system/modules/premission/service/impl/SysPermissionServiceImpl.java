package org.jeeasy.system.modules.premission.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.domain.model.QueryPageModel;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.jeeasy.system.modules.premission.domain.SysPermission;
import org.jeeasy.system.modules.premission.domain.vo.MenuVo;
import org.jeeasy.system.modules.premission.mapper.SysPermissionMapper;
import org.jeeasy.system.modules.premission.service.SysPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单权限表服务接口实现
 *
 * @author AlpsDDJ
 * @description 菜单权限
 * @since 2020-11-21 13:52:05
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public List<SysPermission> queryAllChildren(String parentId) {
        QueryWrapper<SysPermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysPermission::getParentId, parentId).orderByAsc(SysPermission::getSortNo);
        if (StrUtil.isEmpty(parentId) || "0".equals(parentId)) {
            wrapper.lambda().or().isNull(SysPermission::getParentId);
        }
        List<SysPermission> list = list(wrapper);
        permissionFilter(list);
        return list;
    }


    @Override
    public IPage<SysPermission> queryPageTreeList(QueryPageModel query, HttpServletRequest req) {
        QueryWrapper<SysPermission> wrapper = QueryGenerator.createWrapper(SysPermission.class, req.getParameterMap());
        if (StrUtil.isEmpty(req.getParameter("parentId"))) {
            wrapper.lambda().eq(SysPermission::getParentId, "0");
        }
        wrapper.lambda().orderByAsc(SysPermission::getSortNo);
        Page<SysPermission> page = this.page(query.getPage(SysPermission.class), wrapper);
        List<SysPermission> list = page.getRecords();
        permissionFilter(list);
        return page;
    }

    /**
     * 过滤权限列表，将每个权限的子权限加载到其children属性中。
     * 此方法不改变传入权限列表的结构，而是通过查询子权限来丰富每个权限的子权限信息。
     * 仅对非叶子节点的权限查询并设置其子权限，叶子节点权限不进行处理。
     *
     * @param list 待处理的权限列表，列表中的每个元素都是一个SysPermission对象。
     */
    private void permissionFilter(List<SysPermission> list) {
        // 使用流式处理权限列表，过滤出非叶子节点
        list.stream().filter(perm -> !BooleanEnum.yes(perm.getLeaf())).forEach(perm -> {
            // 根据当前权限的id查询其所有子权限
            List<SysPermission> children = queryAllChildren(perm.getId());
            // 如果查询到子权限且不为空，则设置到当前权限的children属性中
            if (children != null && !children.isEmpty()) {
                perm.setChildren(children);
            }
        });
    }


    @Override
    public List<SysPermission> queryByRoleId(String roleId) {
        return baseMapper.queryByRoleId(roleId);
    }

    @Override
    public List<SysPermission> queryByUserId(String userId) {
        return baseMapper.queryByUserId(userId);
    }


    @Override
    public List<MenuVo> queryMenuByUserId(String userId) {
        return baseMapper.queryMenuByUserId(userId, "0");
    }
}