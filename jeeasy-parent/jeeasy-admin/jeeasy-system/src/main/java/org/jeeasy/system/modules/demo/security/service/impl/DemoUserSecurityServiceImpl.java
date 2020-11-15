package org.jeeasy.system.modules.demo.security.service.impl;

import lombok.Setter;
import org.jeeasy.auth.domain.Permission;
import org.jeeasy.auth.service.IAuthService;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.system.modules.demo.security.model.DemoUser;
import org.jeeasy.system.modules.demo.security.model.DemoUserDetails;
import org.jeeasy.system.modules.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Alps
 */
@Component
public class DemoUserSecurityServiceImpl implements IAuthService<DemoUserDetails> {

    @Autowired
    ISysUserService sysUserService;

    @Setter
    @Value("${jeeasy.system.enable-captcha}")
    private boolean enableCaptcha = true;

    @Override
    public String getAuthMethod() {
        return "demo";
    }

    @Override
    public DemoUserDetails getUserByUsername(String username) {
        DemoUser demoUser = new DemoUser().setUsername(username).setId(Tools.uuid()).setPassword(Tools.uuid());
        if (Tools.isNotEmpty(demoUser)) {
            return DemoUserDetails.create(demoUser);
        }
        return null;
    }

    @Override
    public DemoUserDetails verifyLogin(Authentication authentication) {
        return new DemoUserDetails();
//        try {
//            // 验证码
//            if (enableCaptcha) {
//                String captcha = ServletUtil.getRequest().getParameter("captcha");
//                String captchaKey = ServletUtil.getRequest().getParameter("captchaKey");
//                Tools.verifyCaptcha(captchaKey, captcha);
//            }
//        } catch (JeeasyException e) {
//            throw new BadCredentialsException(e.getMessage());
//        }
//
//        String username = (String) authentication.getPrincipal();
//        String password = (String) authentication.getCredentials();
//
//        DemoUserDetails sysUser = this.getUserByUsername(username);
//
//        if (Tools.isEmpty(sysUser)) {
//            throw new UsernameNotFoundException("用户名不存在.");
//        }
//
//        if (!sysUser.izEnabled()) {
//            throw new DisabledException("账号已冻结.");
//        }
////        if(SysUserUtil.create(sysUser).checkPassword(password)){
//        if (SysUserUtil.checkPassword(username, password, sysUser.getPassword(), sysUser.getSalt())) {
//            return sysUser;
//        } else {
//            throw new BadCredentialsException("密码错误.");
//        }
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
    public void onAuthenticationSuccess(DemoUserDetails securityUserDetails) {

    }

}
