package org.jeeasy.system.modules.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.common.core.config.constant.CommonConstant;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.jeeasy.system.modules.user.domain.SysUser;
import org.jeeasy.system.modules.user.domain.SysUserRole;
import org.jeeasy.system.modules.user.mapper.SysUserMapper;
import org.jeeasy.system.modules.user.mapper.SysUserRoleMapper;
import org.jeeasy.system.modules.user.service.SysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @Override
//    @Cacheable(value = CommonConstant.CACHE_USER_KEY, key= "#username")
    public SysUser getByUserName(String username) {
        return baseMapper.selectOne(QueryGenerator.createWrapper(SysUser.class).lambda().eq(SysUser::getUsername, username));
    }

    @Override
    @Cacheable(value = CommonConstant.CACHE_SYS_USER_KEY, key= "#id")
    public SysUser getByUserId(String id) {
        return baseMapper.selectById(id);
    }


    @Override
    public boolean checkPasswordByUserName(String username, String password) {
        SysUser sysUser = baseMapper.selectOne(QueryGenerator.createWrapper(SysUser.class).lambda().eq(SysUser::getUsername, username));
        if (Tools.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        return SysUserUtil.create(sysUser).checkPassword(password);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserWithRole(SysUser sysUser, String roles) {
        this.save(sysUser);
        if(Tools.isNotEmpty(roles)){
            Arrays.stream(StrUtil.split(roles, ",")).forEach(roleId -> {
                new SysUserRole(sysUser.getId(), roleId).insert();
            });
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUserWithRole(SysUser sysUser, String roles) {
        this.updateById(sysUser);
        // 删除用户原有角色
        new SysUserRole().delete(QueryGenerator.createWrapper(SysUserRole.class).lambda().eq(SysUserRole::getUserId, sysUser.getId()));
        if(Tools.isNotEmpty(roles)){
            Arrays.stream(StrUtil.split(roles, ",")).forEach(roleId -> {
                new SysUserRole(sysUser.getId(), roleId).insert();
            });
        }
    }
}
