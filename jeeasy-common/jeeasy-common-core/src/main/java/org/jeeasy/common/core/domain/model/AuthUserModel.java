package org.jeeasy.common.core.domain.model;

import lombok.Data;

/**
 * 认证用户模型
 *
 * @author wei.yang
 * @date 2022-09-10 12:35
 */
@Data
public class AuthUserModel {
    private String username;
    private String password;
    private String type;
}
