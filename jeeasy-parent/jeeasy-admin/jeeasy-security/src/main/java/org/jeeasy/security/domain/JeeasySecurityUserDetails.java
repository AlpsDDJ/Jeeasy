package org.jeeasy.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
public abstract class JeeasySecurityUserDetails<U extends JeeasyBaseSecurityUserDetails> implements UserDetails {

    @Getter
    @Setter
    private U user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.authorities();
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    @Override
    public String getUsername() {
        return user.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.izAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.izAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.izCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.izEnabled();
    }
}
