package org.jeeasy.security.support;

import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
import org.jeeasy.security.service.IJeeasySecurityService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Describe: 自定义 Security 登陆认证实现
 *
 * @author Alps
 */
//@Component
public class JeeasySecurityAuthenticationProvider<U extends JeeasyBaseSecurityUserDetails> implements AuthenticationProvider {

//    @Autowired
    private IJeeasySecurityService<U> securityService;

    public JeeasySecurityAuthenticationProvider(IJeeasySecurityService<U> securityService) {
        this.securityService = securityService;
    }

    /**
     * Describe: 自定义 Security 登陆逻辑
     * Param: Authentication
     * Return Authentication
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        U userDetails = securityService.verifyLogin(authentication);
        if (Tools.isNotEmpty(userDetails)) {
            return new UsernamePasswordAuthenticationToken(userDetails, userDetails.password(), userDetails.authorities());
        } else {
            throw new JeeasyException("登录失败");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
