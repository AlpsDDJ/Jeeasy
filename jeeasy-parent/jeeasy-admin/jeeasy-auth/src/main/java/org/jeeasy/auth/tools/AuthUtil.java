package org.jeeasy.auth.tools;

import org.jeeasy.auth.domain.IAuthUser;
import org.jeeasy.common.core.constant.CommonConstant;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author AlpsDDJ
 * @date 2020/11/16
 */
public class AuthUtil {

    public static final String SESSION_USER_KEY = CommonConstant.CACHE_USER_KEY;



    public static IAuthUser curr(){
        // 获取用户认证信息对象。
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        SecurityContextHolder.getContext().setAuthentication();
        return null;
    }


}
