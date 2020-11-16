package org.jeeasy.system.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.common.core.constant.CommonConstant;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.db.tools.QueryGenerator;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.mapper.SysUserMapper;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.jeeasy.system.tools.SysUserUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    @Cacheable(value = CommonConstant.CACHE_USER_KEY, key= "#username")
    public SysUser getByUserName(String username) {
        return this.getOne(QueryGenerator.createWrapper(SysUser.class).lambda().eq(SysUser::getUsername, username));
    }

//    @Override
//    public boolean checkPasswordById(String id, String password) {
//        SysUser sysUser = this.getById(id);
//        return SysUserUtil.create(sysUser).checkPassword(password);
//    }

    @Override
    @CacheEvict(value = CommonConstant.CACHE_USER_KEY, key= "#username")
    public boolean checkPasswordByUserName(String username, String password) {
        SysUser sysUser = this.getByUserName(username);
        if (Tools.isEmpty(sysUser)) {
            throw new UsernameNotFoundException("用户名不存在.");
        }
        return SysUserUtil.create(sysUser).checkPassword(password);
    }

//    @Override
//    public SysUser login(String username, String password) {
//        SysUser sysUser = this.getByUserName(username);
//        if (Tools.isEmpty(sysUser)) {
//            throw new JeeasyException("用户名不存在.");
//        }
//        if (SysUserUtil.create(sysUser).checkPassword(password)) {
//            return sysUser;
//        } else {
//            throw new JeeasyException("密码错误.");
//        }
//    }

//    /**
//     * 登录处理
//     * @param s
//     * @return
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public JeeasySysUserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        SysUser sysUser = getByUserName(s);
//        JeeasySysUserDetails jeeasySysUserDetails = JeeasySysUserDetails.create(sysUser);
//        return jeeasySysUserDetails;
//    }
}
