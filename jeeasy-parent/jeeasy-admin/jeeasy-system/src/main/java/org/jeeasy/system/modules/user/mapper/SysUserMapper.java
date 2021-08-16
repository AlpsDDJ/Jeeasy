package org.jeeasy.system.modules.user.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.jeeasy.system.modules.user.domain.SysUser;
import org.jeeasy.system.modules.user.domain.model.SysUserQueryPageModel;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    IPage<SysUser> querySysUserVoPage(IPage<?> page, @Param(Constants.WRAPPER) QueryWrapper<SysUser> wrapper, @Param("model") SysUserQueryPageModel model);
}
