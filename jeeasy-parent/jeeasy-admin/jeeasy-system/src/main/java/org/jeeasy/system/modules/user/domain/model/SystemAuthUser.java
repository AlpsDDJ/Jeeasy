package org.jeeasy.system.modules.user.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeeasy.common.core.entity.IAuthUser;
import org.jeeasy.system.enums.user.SysUserStatusEnum;
import org.jeeasy.system.modules.user.domain.SysUser;

import java.util.Collection;

/**
 * @author Alps
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class SystemAuthUser extends SysUser implements IAuthUser {

    private Collection<String> roles;
    private Collection<String> permissions;

    @Override
    public String id() {
        return this.getId();
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

    @Override
    public boolean izEnabled(){
        return SysUserStatusEnum.NORMAL.getValue().equals(this.getStatus());
    }

}
