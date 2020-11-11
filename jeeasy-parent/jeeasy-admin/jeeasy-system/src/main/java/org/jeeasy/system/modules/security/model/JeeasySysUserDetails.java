package org.jeeasy.system.modules.security.model;

import cn.hutool.core.bean.BeanUtil;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

public class JeeasySysUserDetails extends SysUser implements Serializable, UserDetails {

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
        return super.getStatus().equals("0");
    }
}
