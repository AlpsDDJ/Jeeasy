package org.jeeasy.system.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.user.entity.SysUser;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public interface ISysUserService extends IService<SysUser> {
    SysUser getByUserName(String username);

//    boolean checkPasswordById(String id, String password);

    boolean checkPasswordByUserName(String username, String password);

//    SysUser login(String username, String password);
}
