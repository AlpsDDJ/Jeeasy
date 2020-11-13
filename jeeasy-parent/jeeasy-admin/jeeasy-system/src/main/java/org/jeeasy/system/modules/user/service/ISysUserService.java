package org.jeeasy.system.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public interface ISysUserService extends IService<SysUser>, UserDetailsService {
    SysUser getByUserName(String userName);

//    boolean checkPasswordById(String id, String password);

//    boolean checkPasswordByUserName(String userName, String password);

//    SysUser login(String username, String password);
}
