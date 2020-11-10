package org.jeeasy.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Describe: Security 用户实体
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
public class SecurityUserDetails<T extends UserDetails> implements UserDetails{

    @Getter
    @Setter
    private T user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return  user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        if(user.isEnabled()){
            return true;
        }else{
            return false;
        }
    }

}
