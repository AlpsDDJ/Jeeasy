package org.jeeasy.system.modules.demo.security.model;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;
import org.jeeasy.auth.annotation.AuthMethod;
import org.jeeasy.auth.domain.IAuthUser;

/**
 * @author Alps
 */
@Data
public class DemoUserDetails extends DemoUser implements IAuthUser {

    public static DemoUserDetails create(DemoUser sysUser) {
        return BeanUtil.toBean(sysUser, DemoUserDetails.class);
    }

//    @Override
//    public Collection<? extends GrantedAuthority> authorities() {
//        return null;
//    }

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

//    @Override
//    public UserDetails toAuthUserDetails() {
//        return null;
//    }
}
