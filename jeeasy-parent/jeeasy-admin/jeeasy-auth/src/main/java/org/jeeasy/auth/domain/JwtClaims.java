package org.jeeasy.auth.domain;

import cn.hutool.core.bean.BeanUtil;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description JwtClaims
 * @date 2020-11-20
 */
@Setter
@Getter
//@Builder
@Accessors(chain = true)
public class JwtClaims {

    private String id;

    private String username;
    /**
     * 用户角色
     */
    private Collection<String> roles;

    /**
     * 用户权限(码)
     */
    private Collection<String> permissions;

    /**
     * 认证方法
     */
    private String authMethod;

    /**
     * 认证次数
     * 同账号多处登录记录编号
     */
    private Integer authNumber;

    public static JwtClaims build(Claims claims) {
        return BeanUtil.mapToBean(claims, JwtClaims.class, true).setId(claims.getId()).setUsername(claims.getSubject());
    }

//    public Collection<String> getRoles() {
//        if (Tools.isEmpty(roles)) {
//            return this.get("roles", Collection.class);
//        }
//        return roles;
//    }
//
//    public Collection<String> getPermissions() {
//        if (Tools.isEmpty(permissions)) {
//            return this.get("permissions", Collection.class);
//        }
//        return permissions;
//    }
//
//    public String getAuthMethod() {
//        if (Tools.isEmpty(authMethod)) {
//            return this.get("authMethod", String.class);
//        }
//        return authMethod;
//    }
//
//    public Integer getAuthNumber() {
//        if (Tools.isEmpty(authNumber)) {
//            return this.get("authNumber", Integer.class);
//        }
//        return authNumber;
//    }
//
}