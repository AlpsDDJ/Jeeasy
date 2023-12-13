package org.jeeasy.system.modules.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.jeeasy.common.core.config.constant.CommonConstant;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.handler.userpwd.UserPasswordHandler;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.user.domain.SysUser;
import org.jeeasy.system.modules.user.domain.SysUserDept;
import org.jeeasy.system.modules.user.domain.SysUserRole;
import org.jeeasy.system.modules.user.domain.model.SysUserQueryPageModel;
import org.jeeasy.system.modules.user.domain.model.UserInfoModel;
import org.jeeasy.system.modules.user.mapper.SysUserMapper;
import org.jeeasy.system.modules.user.service.SysUserDeptService;
import org.jeeasy.system.modules.user.service.SysUserRoleService;
import org.jeeasy.system.modules.user.service.SysUserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    @Lazy
    private SysUserService userService;
    @Resource
    private SysUserRoleService userRoleService;
    @Resource
    private SysUserDeptService userDeptService;

    @Override
    public IPage<SysUser> querySysUserVoPage(QueryWrapper<SysUser> wrapper, SysUserQueryPageModel model) {
        return baseMapper.querySysUserVoPage(model.getPage(SysUser.class), wrapper, model);
    }

    @Override
//    @Cacheable(value = CommonConstant.CACHE_USER_KEY, key= "#username")
    public SysUser getByUserName(String username) {
        return baseMapper.selectOne(QueryGenerator.createWrapper(SysUser.class).lambda().eq(SysUser::getUsername, username));
    }

    @Override
    @Cacheable(value = CommonConstant.CACHE_SYS_USER_KEY, key = "#id")
    public SysUser getByUserId(String id) {
        return baseMapper.selectById(id);
    }


    @Override
    public boolean checkPasswordByUserName(String username, String password) {
        SysUser sysUser = baseMapper.selectOne(QueryGenerator.createWrapper(SysUser.class).lambda().eq(SysUser::getUsername, username));
        if (Tools.isEmpty(sysUser)) {
            throw new JeeasyException("用户名不存在");
        }
        return UserPasswordHandler.create(sysUser).checkPassword(password);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserWithUserInfoModel(UserInfoModel model) {
        // 初始化用户密码
        SysUser sysUser = UserPasswordHandler.create(model.getUser()).initSaltAndPassword();
        userService.save(sysUser);
        userService.saveUserRolesAndDepts(model.getRoles(), model.getDepts(), sysUser.getId(), true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserWithUserInfoModel(UserInfoModel model) {
        SysUser sysUser = model.getUser();
        userService.updateById(sysUser);
        userService.saveUserRolesAndDepts(model.getRoles(), model.getDepts(), sysUser.getId(), false);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUserRolesAndDepts(String[] roles, String[] depts, String userId, boolean isInsert) {
        // 修改用户时删除原有 角色 和 部门信息
        if (!isInsert) {
            userRoleService.remove(QueryGenerator.createWrapper(SysUserRole.class).lambda().eq(SysUserRole::getUserId, userId));
            userDeptService.remove(QueryGenerator.createWrapper(SysUserDept.class).lambda().eq(SysUserDept::getUserId, userId));
        }
        if (Tools.isNotEmpty(roles)) {
            List<SysUserRole> sysUserRoles = new ArrayList<>();
            Arrays.stream(roles).forEach(roleId -> {
                sysUserRoles.add(new SysUserRole(userId, roleId));
            });
            userRoleService.saveBatch(sysUserRoles, 100);
        }
        if (Tools.isNotEmpty(depts)) {
            List<SysUserDept> sysUserDepts = new ArrayList<>();
            Arrays.stream(depts).forEach(deptId -> {
                sysUserDepts.add(new SysUserDept(userId, deptId));
            });
            userDeptService.saveBatch(sysUserDepts, 100);
        }
    }


}
