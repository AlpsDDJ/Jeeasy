package org.jeeasy.auth.domain;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description JeeasyAuthUser 自定义认证用户接口
 * @date 2020-11-14
 */
public interface IAuthUser {

    public static <T extends IAuthUser> T create(Object user, Class<T> clazz){
        return BeanUtil.toBean(user, clazz);
    }

    Collection<? extends GrantedAuthority> authorities();

    String password();

    String username();

    default boolean izAccountNonExpired(){
        return false;
    }

    default boolean izAccountNonLocked(){
        return false;
    }
    default boolean izCredentialsNonExpired(){
        return false;
    }

    default boolean izEnabled(){
        return true;
    }

    default SecurityUserDetails<?> toAuthUserDetails(){
        return new SecurityUserDetails<>(this);
    }

}
