package org.jeeasy.security.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
public interface JeeasyBaseSecurityUserDetails {
    Collection<? extends GrantedAuthority> authorities();

    String password();

    String username();

    boolean izAccountNonExpired();

    boolean izAccountNonLocked();

    boolean izCredentialsNonExpired();

    boolean izEnabled();
}
