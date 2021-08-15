package org.jeeasy.system.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.user.domain.SysUser;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public interface SysUserService extends IService<SysUser> {
    SysUser getByUserName(String username);

    SysUser getByUserId(String id);

    boolean checkPasswordByUserName(String username, String password);

    void addUserWithRole(SysUser sysUser, String roles);

    void editUserWithRole(SysUser sysUser, String roles);
}
