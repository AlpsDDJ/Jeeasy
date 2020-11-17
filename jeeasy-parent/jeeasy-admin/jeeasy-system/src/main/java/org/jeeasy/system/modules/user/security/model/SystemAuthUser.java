package org.jeeasy.system.modules.user.security.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.auth.domain.IAuthUser;
import org.jeeasy.system.modules.user.entity.SysUser;

/**
 * @author Alps
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SystemAuthUser extends SysUser implements IAuthUser {

//    private Set<String> roles;
//    private Set<String> permissions;

//    @Override
//    public Collection<? extends GrantedAuthority> authorities() {
//        if(Tools.isEmpty(roles)){
//             roles = new HashSet<>();
//        }
//        Set<SimpleGrantedAuthority> authoritieSet = new HashSet<>();
//        permissions.forEach(p -> {
//            authoritieSet.add(new SimpleGrantedAuthority(p));
//        });
//        return authoritieSet;
//    }

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
        return true;
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
