package org.jeeasy.system.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeeasy.system.modules.user.entity.SysUser;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public interface ISysUserService extends IService<SysUser> {
    SysUser queryByUserName(String userName);
}
