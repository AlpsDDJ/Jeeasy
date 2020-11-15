package org.jeeasy.auth.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
@Builder
public class SecurityUserDetails<U extends IAuthUser> implements UserDetails {

    @Getter
//    @Setter
    private U user;

    public SecurityUserDetails(U user) {
        this.user = user;
    }

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
