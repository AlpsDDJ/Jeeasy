package org.jeeasy.auth.domain;

import lombok.*;
import org.jeeasy.common.core.tools.Tools;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUserDetails<U extends IAuthUser> implements UserDetails {

    private U authUser;

    private Set<String> roles;

    private Set<String> permissions;


    public SecurityUserDetails(U authUser) {
        this.authUser = authUser;
//        SecurityUserDetails.builder().permissions();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = new HashSet<>();
        if (Tools.isNotEmpty(roles)) {
            roles.forEach(role -> {
                simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority("ROLE_" + role));
            });
        }
        if (Tools.isNotEmpty(permissions)) {
            permissions.forEach(p -> {
                simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority(p));
            });
        }
        return simpleGrantedAuthoritySet;
    }

    public String getId() {
        return authUser.id();
    }

    @Override
    public String getPassword() {
        return authUser.password();
    }

    @Override
    public String getUsername() {
        return authUser.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authUser.izAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return authUser.izAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authUser.izCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return authUser.izEnabled();
    }
}
