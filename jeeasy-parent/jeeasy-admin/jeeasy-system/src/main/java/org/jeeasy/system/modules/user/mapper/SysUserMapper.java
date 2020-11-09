package org.jeeasy.system.modules.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeeasy.system.modules.user.entity.SysUser;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名查找系统用户
     * @param userName 用户名
     * @return SysUser 系统用户
     */
    SysUser queryByUserName(@Param("userName") String userName);
}
