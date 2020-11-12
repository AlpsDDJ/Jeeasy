package org.jeeasy.system.modules.security.model;

import cn.hutool.core.bean.BeanUtil;
import org.jeeasy.security.domain.JwtUserDetails;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Alps
 */
public class JeeasySysUserDetails extends SysUser implements JwtUserDetails {

    public static JeeasySysUserDetails create(SysUser sysUser) {
        return BeanUtil.toBean(sysUser, JeeasySysUserDetails.class);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return this.izEnabled();
    }
}
