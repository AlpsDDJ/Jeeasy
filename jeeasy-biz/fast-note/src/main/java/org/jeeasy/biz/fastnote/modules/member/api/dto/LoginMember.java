package org.jeeasy.biz.fastnote.modules.member.api.dto;

import lombok.Data;
import org.jeeasy.biz.fastnote.modules.member.domain.FnMember;
import org.jeeasy.common.core.domain.IAuthUser;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户登录信息
 *
 * @author wei.yang
 * @date 2022-09-12 0:58
 */
@Data
public class LoginMember extends FnMember implements IAuthUser {

    /**
     * FastNote 用户虚拟角色
     */
    public static final String VIRTUAL_MEMBER_ROLE = "ROLE_FN_MEMBER";


    private Collection<String> roleSet;
    private Collection<String> permissionSet;

    @Override
    public Collection<String> getRoleSet() {
        Set<String> roles = new HashSet<>();
        roles.add(VIRTUAL_MEMBER_ROLE);
        return roles;
    }

    @Override
    public Collection<String> getPermissionSet() {
        Set<String> permss = new HashSet<>();
        permss.add(VIRTUAL_MEMBER_ROLE);
        return permss;
    }

    @Override
    public String id() {
        return super.getId();
    }

    @Override
    public String password() {
        return super.getPassword();
    }

    @Override
    public String username() {
        return super.getUsername();
    }
}
