package org.jeeasy.system.modules.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeeasy.system.modules.user.domain.SysUser;
import org.jeeasy.system.modules.user.domain.model.SysUserQueryPageModel;
import org.jeeasy.system.modules.user.domain.model.UserInfoModel;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> querySysUserVoPage(QueryWrapper<SysUser> wrapper, SysUserQueryPageModel model);

    SysUser getByUserName(String username);

    SysUser getByUserId(String id);

    boolean checkPasswordByUserName(String username, String password);

    void addUserWithUserInfoModel(UserInfoModel sysUser);

    void editUserWithUserInfoModel(UserInfoModel sysUser);
}
