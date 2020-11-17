package org.jeeasy.system.modules.demo.security.service.impl;

import lombok.Setter;
import org.jeeasy.auth.domain.IAuthUser;
import org.jeeasy.auth.domain.Permission;
import org.jeeasy.auth.service.IAuthService;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.demo.security.model.DemoUser;
import org.jeeasy.system.modules.demo.security.model.DemoUserDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Alps
 */
@Component("demoUserSecurityService")
public class DemoUserSecurityServiceImpl implements IAuthService<DemoUserDetails> {

//    @Autowired
//    ISysUserService sysUserService;

    @Setter
    @Value("${jeeasy.system.enable-captcha}")
    private boolean enableCaptcha = true;

    @Override
    public DemoUserDetails getAuthUserByUsername(String username) {
        DemoUser demoUser = new DemoUser().setUsername(username).setId(Tools.uuid()).setPassword(Tools.uuid());
        if (Tools.isNotEmpty(demoUser)) {
            return DemoUserDetails.create(demoUser);
        }
        return null;
    }

    @Override
    public boolean verifyLogin(Authentication authentication) {
        // TODO 登录验证
        return true;
    }

    @Override
    public Set<String> getRoleSetByUsername(String username) {
        return null;
    }

    @Override
    public Set<String> getPermissionSetByUsername(String username) {
        return null;
    }

    @Override
    public Set<Permission> getAllPermission() {
        return null;
    }

    @Override
    public void onAuthenticationSuccess(IAuthUser securityUserDetails) {

    }

}
