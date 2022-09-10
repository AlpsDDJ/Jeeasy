package org.jeeasy.system.modules.user.domain.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.system.modules.dept.domain.SysDept;
import org.jeeasy.system.modules.role.domian.SysRole;
import org.jeeasy.system.modules.user.domain.SysUser;

import java.util.List;

/**
 * @author mobie
 */
@Data
@ApiModel("系统用户VO")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class SysUserVo extends SysUser {
    private List<SysRole> roleList;
    private List<SysDept> deptList;
}
