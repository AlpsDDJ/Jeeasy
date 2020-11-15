package org.jeeasy.auth.provider;

import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 *
 * 自定义身份认证
 *
 * @author AlpsDDJ
 * @version v1.0
 * @description JeeasyAuthenticationProvider
 * @date 2020-11-14
 */
@Component
public class JeeasyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails userDetails = authServiceProvider.getAuthService(authentication).verifyLogin(authentication).toAuthUserDetails();
        if (Tools.isNotEmpty(userDetails)) {
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        } else {
            throw new JeeasyException("登录失败.");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
