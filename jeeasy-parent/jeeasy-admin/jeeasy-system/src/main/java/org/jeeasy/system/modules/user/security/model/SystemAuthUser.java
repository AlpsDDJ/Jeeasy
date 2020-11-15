package org.jeeasy.system.modules.user.security.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.jeeasy.auth.domain.IAuthUser;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.user.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Alps
 */
@Data
@Accessors(chain = true)
public class SystemAuthUser extends SysUser implements IAuthUser {

//    public static SystemAuthUser create(SysUser sysUser) {
//        return BeanUtil.toBean(sysUser, SystemAuthUser.class);
//    }

    private Collection<? extends GrantedAuthority> roles;

    @Override
    public Collection<? extends GrantedAuthority> authorities() {
        if(Tools.isEmpty(roles)){
             roles = new HashSet<>();
        }
        return roles;
    }

    @Override
    public String password() {
        return this.getPassword();
    }

    @Override
    public String username() {
        return this.getUsername();
    }

//    @Override
//    public boolean izAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean izAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean izCredentialsNonExpired() {
//        return false;
//    }

//    @Override
//    public UserDetails toAuthUserDetails() {
//        return null;
//    }
}
