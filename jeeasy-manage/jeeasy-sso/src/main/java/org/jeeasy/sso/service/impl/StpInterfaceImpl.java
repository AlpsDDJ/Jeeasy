package org.jeeasy.sso.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.ListUtil;
import org.jeeasy.common.core.domain.IAuthUser;
import org.jeeasy.common.core.service.IAuthService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2022-09-12 11:40
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        IAuthUser authUser = (IAuthUser)StpUtil.getSession().get(IAuthService.SESSION_USER_KEY);
        return ListUtil.toList(authUser.getPermissionSet());
    }

    @Override
    public List<String> getRoleList(Object o, String s) {
        IAuthUser authUser = (IAuthUser)StpUtil.getSession().get(IAuthService.SESSION_USER_KEY);
        return ListUtil.toList(authUser.getRoleSet());
    }

}
