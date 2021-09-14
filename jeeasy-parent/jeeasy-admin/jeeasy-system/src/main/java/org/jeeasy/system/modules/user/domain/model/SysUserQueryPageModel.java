package org.jeeasy.system.modules.user.domain.model;

import lombok.Getter;
import lombok.Setter;
import org.jeeasy.common.core.domain.model.QueryPageModel;

/**
 * @author AlpsDDJ
 * @date 2021/8/16 9:49
 */
@Getter
@Setter
public class SysUserQueryPageModel extends QueryPageModel {

    private String roleId;
    private String departId;

}
