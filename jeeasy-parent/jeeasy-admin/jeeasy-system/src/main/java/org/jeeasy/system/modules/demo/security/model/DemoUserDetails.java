package org.jeeasy.system.modules.demo.security.model;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author Alps
 */
@Data
public class DemoUserDetails extends DemoUser implements JeeasyBaseSecurityUserDetails {

    public static DemoUserDetails create(DemoUser sysUser) {
        return BeanUtil.toBean(sysUser, DemoUserDetails.class);
    }
    @Override
    public Collection<? extends GrantedAuthority> authorities() {
        return null;
    }

    @Override
    public String password() {
        return null;
    }

    @Override
    public String username() {
        return null;
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

    @Override
    public boolean izEnabled() {
        return false;
    }
}
