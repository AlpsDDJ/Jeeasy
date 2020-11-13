package org.jeeasy.system.modules.security.model;

import lombok.Data;
import org.jeeasy.security.domain.JeeasySecurityUserDetails;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Alps
 */
@Data
public class JeeasySysUserDetails extends JeeasySecurityUserDetails {

    private SysUser sysUser;

    public JeeasySysUserDetails(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public String getSalt(){
        return this.sysUser.getSalt();
    }

    public static JeeasySysUserDetails create(SysUser sysUser) {
        return new JeeasySysUserDetails(sysUser);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return getPassword();
    }

    @Override
    public String getUsername() {
        return sysUser.getUsername();
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
        return false;
    }
}
