package org.jeeasy.auth.service.impl;

import org.jeeasy.auth.tools.SecurityUtil;
import org.jeeasy.common.core.entity.IAuthUser;
import org.jeeasy.common.core.service.CurrentAuthUserService;
import org.springframework.stereotype.Component;

/**
 * @author AlpsDDJ
 * @date 2020/11/24 9:16
 */
@Component
public class CurrentAuthUserServiceImpl implements CurrentAuthUserService {
    @Override
    public IAuthUser getCurrentAuthUser() {
        return SecurityUtil.getCurrentAuthUser();
    }
}
