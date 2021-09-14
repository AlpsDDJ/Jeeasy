package org.jeeasy.system.modules.user.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.common.core.config.constant.CommonConstant;
import org.jeeasy.common.core.tools.QueryGenerator;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.user.domain.SysUser;
import org.jeeasy.system.modules.user.domain.SysUserDepart;
import org.jeeasy.system.modules.user.domain.SysUserRole;
import org.jeeasy.system.modules.user.domain.model.SysUserQueryPageModel;
import org.jeeasy.system.modules.user.domain.model.UserInfoModel;
import org.jeeasy.system.modules.user.mapper.SysUserDepartMapper;
import org.jeeasy.system.modules.user.mapper.SysUserMapper;
import org.jeeasy.system.modules.user.mapper.SysUserRoleMapper;
import org.jeeasy.system.modules.user.service.SysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper userRoleMapper;
    @Autowired
    private SysUserDepartMapper userDepartMapper;

    @Override
    public IPage<SysUser> querySysUserVoPage(QueryWrapper<SysUser> wrapper, SysUserQueryPageModel model) {
        return baseMapper.querySysUserVoPage(model.getPage(), wrapper, model);
    }

    public static void main(String[] args) {
        List<String> ls1 = new ArrayList<String>(){};
        ls1.add("1");
        ls1.add("2");
        ls1.add("3");
        ls1.add("4");
        List<String> ls2 = new ArrayList<String>(){};
        ls2.add("1");
        ls2.add("3");
        ls2.add("5");
        ls2.add("7");
        List<String> strings = ls1.stream().filter(ls2::contains).collect(Collectors.toList());
//        List<String> strings = CollectionUtil.addAllIfNotContains(ls1, ls2);
        strings.forEach(System.out::println);
    }

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
    public void addUserWithUserInfoModel(UserInfoModel model) {
        // 初始化用户密码
        SysUser sysUser = SysUserUtil.create(model.getUser()).initSaltAndPassword();
        this.save(sysUser);
        saveUserRolesAndDeparts(model.getRoles(), model.getDeparts(), sysUser.getId(), true);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editUserWithUserInfoModel(UserInfoModel model) {
        SysUser sysUser = model.getUser();
        this.updateById(sysUser);
        saveUserRolesAndDeparts(model.getRoles(), model.getDeparts(), sysUser.getId(), false);
    }

    private void saveUserRolesAndDeparts(String roles, String departs, String userId, boolean isInsert) {
        // 修改用户时删除原有 角色 和 部门信息
        if(!isInsert){
            new SysUserRole().delete(QueryGenerator.createWrapper(SysUserRole.class).lambda().eq(SysUserRole::getUserId, userId));
            new SysUserDepart().delete(QueryGenerator.createWrapper(SysUserDepart.class).lambda().eq(SysUserDepart::getUserId, userId));
        }
        if(Tools.isNotEmpty(roles)){
            Arrays.stream(StrUtil.split(roles, ",")).forEach(roleId -> {
                new SysUserRole(userId, roleId).insert();
            });
        }
        if(Tools.isNotEmpty(departs)){
            Arrays.stream(StrUtil.split(departs, ",")).forEach(departId -> {
                new SysUserDepart(userId, departId).insert();
            });
        }
    }


}
