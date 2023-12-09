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

    /**
     * 根据指定的查询条件和分页信息查询用户对象列表
     *
     * @param wrapper 查询条件包装类
     * @param model   查询条件页面模型对象
     * @return 分页后的用户对象列表
     */
    IPage<SysUser> querySysUserVoPage(QueryWrapper<SysUser> wrapper, SysUserQueryPageModel model);


    /**
     * 根据用户名获取用户对象
     *
     * @param username 用户名
     * @return 用户对象
     */
    SysUser getByUserName(String username);


    /**
     * 根据用户ID获取SysUser对象
     *
     * @param id 用户ID
     * @return SysUser对象
     */
    SysUser getByUserId(String id);


    /**
     * 根据给定的用户名和密码检查密码是否正确
     *
     * @param username 用户名
     * @param password 密码
     * @return 如果密码正确，则返回true，否则返回false
     */
    boolean checkPasswordByUserName(String username, String password);

    /**
     * 插入用户信息模型
     *
     * @param sysUser 待插入的用户信息模型对象
     */
    void insertUserWithUserInfoModel(UserInfoModel sysUser);

    /**
     * 使用UserInfoModel中的信息更新用户信息
     *
     * @param sysUser UserInfoModel对象，包含用户信息
     */
    void updateUserWithUserInfoModel(UserInfoModel sysUser);


    /**
     * 保存用户的角色和部门信息
     *
     * @param roles    用户的角色列表
     * @param depts    用户的部门列表
     * @param userId   用户的ID
     * @param isInsert 是否是插入操作
     */
    void saveUserRolesAndDepts(String[] roles, String[] depts, String userId, boolean isInsert);
}
