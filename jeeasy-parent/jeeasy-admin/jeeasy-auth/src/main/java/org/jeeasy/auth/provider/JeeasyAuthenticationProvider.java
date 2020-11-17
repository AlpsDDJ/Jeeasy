package org.jeeasy.auth.provider;

import org.jeeasy.auth.domain.IAuthUser;
import org.jeeasy.auth.domain.SecurityUserDetails;
import org.jeeasy.auth.service.IAuthService;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
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

//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        IAuthService<?> authService = authServiceProvider.getAuthService(authentication);
        String username = (String) authentication.getPrincipal();
        IAuthUser authUser = authService.verifyLogin(username, authentication);
        SecurityUserDetails<?> userDetails = null;
        if (Tools.isNotEmpty(authUser)) {
            userDetails = authUser.createUserDetails();
//            userDetails = authService.getAuthUserByUsername(username).createUserDetails();
//            userDetails.setRoles(authService.getRoleSetByUsername(username));
//            userDetails.setPermissions(authService.getPermissionSetByUsername(username));
        }
        if (Tools.isNotEmpty(userDetails)) {

            if (!userDetails.isAccountNonExpired()) {
                throw new AccountExpiredException("帐户过期.");
            }

            if (!userDetails.isEnabled()) {
                throw new DisabledException("账号已冻结.");
            }

//            if(!userDetails.isAccountNonLocked()){
//                throw new AccountLockedException("帐户过期.");
//            }

            authService.onAuthenticationSuccess(userDetails.getUser());
//            return authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities())
//            );
            userDetails.setPermissions(authService.getPermissionSetByUsername(username));
            userDetails.setRoles(authService.getRoleSetByUsername(username));
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
