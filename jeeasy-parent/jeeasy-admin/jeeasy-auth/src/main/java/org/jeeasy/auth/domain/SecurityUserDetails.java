package org.jeeasy.auth.domain;

import lombok.*;
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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUserDetails<U extends IAuthUser> implements UserDetails {

    @Getter
//    @Setter
    private U user;

    @Getter
    @Setter
    private Set<String> roles;

    @Getter
    @Setter
    private Set<String> permissions;


    @Getter
    @Setter
    private String issuer;

    public SecurityUserDetails(U user) {
        this.user = user;
//        SecurityUserDetails.builder().permissions();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = new HashSet<>();
        permissions.forEach(role -> {
            simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority(role));
        });
        return simpleGrantedAuthoritySet;
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
