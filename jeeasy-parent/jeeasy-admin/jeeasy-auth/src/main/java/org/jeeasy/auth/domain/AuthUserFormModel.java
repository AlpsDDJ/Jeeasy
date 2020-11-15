package org.jeeasy.auth.domain;

import lombok.Data;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description AuthUserFormModel
 * @date 2020-11-15
 */
@Data
public class AuthUserFormModel {

    private String username;

    private String password;

    private String authMethod;

}
