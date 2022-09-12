package org.jeeasy.biz.fastnote.modules.member.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.biz.fastnote.modules.member.api.dto.LoginMember;
import org.jeeasy.biz.fastnote.modules.member.domain.FnMember;
import org.jeeasy.biz.fastnote.modules.member.service.FnMemberService;
import org.jeeasy.common.core.domain.IAuthUser;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.annotation.auth.AuthType;
import org.jeeasy.common.core.domain.Permission;
import org.jeeasy.common.core.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * FastNote 用户认证处理逻辑
 *
 * @author wei.yang
 * @date 2022-09-12 1:06
 */
@Slf4j
@Component("fnMemberAuthService")
@AuthType(type = AuthType.Type.FM_MEMBER)
public class FnMemberAuthServiceImpl implements IAuthService<LoginMember> {

    @Autowired
    private FnMemberService memberService;

    @Override
    public LoginMember getAuthUserByUsername(String phone) {
        FnMember member = memberService.findMemberByPhone(phone);
        if (Tools.isNotEmpty(member)) {
            return IAuthUser.create(member, LoginMember.class);
        }
        return null;
    }

    @Override
    public LoginMember login(String username, String password) {
        FnMember member = memberService.login(username, password);
        return IAuthUser.create(member, LoginMember.class);
    }

    @Override
    public Set<String> getRoleSetByUsername(String username) {
        return null;
    }

    @Override
    public Set<String> getPermissionSetByUsername(String username) {
        return null;
    }

    @Override
    public Set<Permission> getAllPermission() {
        return null;
    }

    @Override
    public void onAuthenticationSuccess(IAuthUser authUser) {
        StpUtil.getSession().set(IAuthService.SESSION_USER_KEY, authUser);
    }
}
