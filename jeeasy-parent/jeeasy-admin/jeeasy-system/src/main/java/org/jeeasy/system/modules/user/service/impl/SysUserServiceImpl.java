package org.jeeasy.system.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.common.db.tools.QueryGenerator;
import org.jeeasy.system.modules.security.model.JeeasySysUserDetails;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.mapper.SysUserMapper;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public SysUser getByUserName(String userName) {
        return this.getOne(QueryGenerator.createWrapper(SysUser.class).lambda().eq(SysUser::getUsername, userName));
    }

    @Override
    public boolean checkPasswordById(String id, String password) {
        SysUser sysUser = this.getById(id);
        return SysUserUtil.create(sysUser).checkPassword(password);
    }

    @Override
    public boolean checkPasswordByUserName(String userName, String password) {
        SysUser sysUser = this.getByUserName(userName);
        return SysUserUtil.create(sysUser).checkPassword(password);
    }

    @Override
    public JeeasySysUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return (JeeasySysUserDetails)getByUserName(s);
    }
}
