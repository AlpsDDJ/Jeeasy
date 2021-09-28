package org.jeeasy.system.modules.premission.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.enums.BooleanEnum;
import org.jeeasy.system.modules.premission.domain.SysPermission;
import org.jeeasy.system.modules.premission.mapper.SysPermissionMapper;
import org.jeeasy.system.modules.premission.service.SysPermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单权限表服务接口实现
 *
 * @author AlpsDDJ
 * @since 2020-11-21 13:52:05
 * @description 菜单权限
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    @Override
    public List<SysPermission> queryAllChildren(String parentId) {
        QueryWrapper<SysPermission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(SysPermission::getParentId, parentId).orderByAsc(SysPermission::getSortNo);
        if(StrUtil.isEmpty(parentId) || "0".equals(parentId)){
            wrapper.lambda().or().isNull(SysPermission::getParentId);
        }
        List<SysPermission> list = list(wrapper);
        list.forEach(perm -> {
            if(!BooleanEnum.yes(perm.getIsLeaf())) {
                List<SysPermission> children = queryAllChildren(perm.getId());
                if(children != null && !children.isEmpty()){
                    perm.setChildren(children);
                }
            }
        });
        return list;
    }
}