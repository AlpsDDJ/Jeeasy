package org.jeeasy.sso.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.jeeasy.common.core.domain.IAuthUser;
import org.jeeasy.common.core.service.CurrentAuthUserService;
import org.springframework.stereotype.Service;

/**
 * @author AlpsDDJ
 * @date 2020/11/24 9:16
 */
@Service
public class CurrentAuthUserServiceImpl implements CurrentAuthUserService {
    @Override
    public IAuthUser getCurrentAuthUser() {
        return StpUtil.getSession().getModel(IAuthUser.SESSION_KEY, IAuthUser.class);
    }
}
