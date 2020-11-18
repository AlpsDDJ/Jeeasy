package org.jeeasy.auth.domain;

import cn.hutool.core.bean.BeanUtil;
import org.jeeasy.auth.annotation.AuthMethod;
import org.jeeasy.common.core.tools.Tools;
import org.springframework.core.annotation.AnnotationUtils;

import java.io.Serializable;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description JeeasyAuthUser 自定义认证用户接口
 * @date 2020-11-14
 */
public interface IAuthUser extends Serializable {

    static <T extends IAuthUser> T create(Object user, Class<T> clazz) {
        return BeanUtil.copyProperties(user, clazz);
    }

//    Collection<? extends GrantedAuthority> authorities();

    String id();

    String password();

    String username();

    default boolean izAccountNonExpired() {
        return true;
    }

    default boolean izAccountNonLocked() {
        return true;
    }

    default boolean izCredentialsNonExpired() {
        return true;
    }

    default boolean izEnabled() {
        return true;
    }

    /**
     * 创建 SecurityUserDetails
     *
     * @return
     */
    default SecurityUserDetails<?> createUserDetails() {
        SecurityUserDetails<IAuthUser> userDetails = new SecurityUserDetails<>(this);
        AuthMethod authMethod = AnnotationUtils.getAnnotation(this.getClass(), AuthMethod.class);
        String issuer = "";
        if(Tools.isNotEmpty(authMethod)){
            issuer = authMethod.method();
        }
        userDetails.setIssuer(issuer);
        return userDetails;
    }

}
