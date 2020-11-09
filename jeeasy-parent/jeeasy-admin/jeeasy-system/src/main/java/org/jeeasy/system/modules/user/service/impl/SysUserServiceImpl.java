package org.jeeasy.system.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.jeeasy.system.modules.user.mapper.SysUserMapper;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    SysUserMapper sysUserMapper;

    @Override
    public SysUser queryByUserName(String userName) {
        return sysUserMapper.queryByUserName(userName);
    }
}
