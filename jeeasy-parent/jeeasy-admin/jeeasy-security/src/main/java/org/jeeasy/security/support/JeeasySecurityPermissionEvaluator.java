package org.jeeasy.security.support;

import org.jeeasy.security.domain.JeeasySecurityUserDetails;
import org.jeeasy.security.service.IJeeasySecurityService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;
import java.util.Set;

/**
 * Describe: 自定义 Security 权限注解实现
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
//@Component
//@NoArgsConstructor
public class JeeasySecurityPermissionEvaluator<U extends JeeasySecurityUserDetails> implements PermissionEvaluator {

    // @Autowired
    private IJeeasySecurityService<U> securityService;

    public JeeasySecurityPermissionEvaluator(IJeeasySecurityService<U> securityService) {
        this.securityService = securityService;
    }

    /**
     * 自定义 Security 权限认证 @HasPermission
     *
     * @param authentication
     * @param o
     * @param o1
     * @return
     */
    @Override
    public boolean hasPermission(Authentication authentication, Object o, Object o1) {
        U securityUserDetails = (U) authentication.getPrincipal();
        Set<String> permissions = securityService.getPermissionSetByUsername(securityUserDetails.getUsername());
//        for (SysPower sysPower :powerList) {
//            permissions.add(sysPower.getPowerCode());
//        }
        if (permissions.contains(o1)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
