package org.jeeasy.auth.tools;

import org.jeeasy.auth.domain.SecurityUserDetails;
import org.jeeasy.common.core.entity.IAuthUser;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.vo.RestCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author AlpsDDJ
 * @date 2020/11/24 9:16
 */
public class SecurityUtil {
    /**
     * 获取当前登录用户信息
     * @return
     */
    public static IAuthUser getCurrentAuthUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(Tools.isEmpty(authentication)){
            throw new JeeasyException(RestCode.NO_USER);
        }
        SecurityUserDetails<IAuthUser> userDetails = (SecurityUserDetails<IAuthUser>)authentication.getPrincipal();
        if(Tools.isEmpty(userDetails)){
            throw new JeeasyException(RestCode.NO_USER);
        }
        return userDetails.getAuthUser();
    }
}
