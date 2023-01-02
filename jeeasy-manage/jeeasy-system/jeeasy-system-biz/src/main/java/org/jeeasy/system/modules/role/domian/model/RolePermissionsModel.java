package org.jeeasy.system.modules.role.domian.model;

import lombok.Data;

@Data
public class RolePermissionsModel {
    private String roleId;
    private String[] permissions;
}
