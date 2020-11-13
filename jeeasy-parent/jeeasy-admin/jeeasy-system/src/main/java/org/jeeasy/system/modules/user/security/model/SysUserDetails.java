package org.jeeasy.system.modules.user.security.model;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Alps
 */
@Data
public class SysUserDetails extends SysUser implements JeeasyBaseSecurityUserDetails {

    public static SysUserDetails create(SysUser sysUser) {
        return BeanUtil.toBean(sysUser, SysUserDetails.class);
    }
    @Override
    public Collection<? extends GrantedAuthority> authorities() {
        return null;
    }

    @Override
    public String password() {
        return this.getPassword();
    }

    @Override
    public String username() {
        return this.getUsername();
    }

    @Override
    public boolean izAccountNonExpired() {
        return false;
    }

    @Override
    public boolean izAccountNonLocked() {
        return false;
    }

    @Override
    public boolean izCredentialsNonExpired() {
        return false;
    }
}
