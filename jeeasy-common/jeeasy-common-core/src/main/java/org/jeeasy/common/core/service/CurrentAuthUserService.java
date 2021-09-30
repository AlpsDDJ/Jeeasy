package org.jeeasy.common.core.service;

import org.jeeasy.common.core.domain.IAuthUser;

/**
 * @author AlpsDDJ
 * @date 2020/11/24 9:10
 */
public interface CurrentAuthUserService {
    /**
     * 获取当前登录用户信息
     * @return
     */
    IAuthUser getCurrentAuthUser();
}
