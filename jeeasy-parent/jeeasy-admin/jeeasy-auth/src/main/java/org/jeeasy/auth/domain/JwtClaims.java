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
    private String authType;

    /**
     * 认证次数
     * 同账号多处登录记录编号
     */
    private Integer authNumber;

    public static JwtClaims build(Claims claims) {
        return BeanUtil.mapToBean(claims, JwtClaims.class, true).setId(claims.getId()).setUsername(claims.getSubject());
    }

}