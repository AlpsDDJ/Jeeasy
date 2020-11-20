package org.jeeasy.auth.controller;

import org.jeeasy.auth.vo.AuthUserFormModel;
import org.jeeasy.auth.tools.JwtUtil;
import org.jeeasy.common.core.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description AuthController
 * @date 2020-11-14
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

//    @Autowired
//    List<IAuthService<?>> authServiceList;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public R<?> auth(@RequestBody AuthUserFormModel userFormModel) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userFormModel.getUsername(), userFormModel.getPassword());
        authenticationToken.setDetails(userFormModel);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.createJwt(authentication, userFormModel.getRememberMe(), false);
        String refreshToken = jwtUtil.createJwt(authentication, userFormModel.getRememberMe(), true);
        Map<String, String> map = new HashMap<>();
        map.put("token", token);
        map.put("refreshToken", refreshToken);
        return R.ok(map).setMessage("登录成功.");
    }

    /**
     * 退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<?> logout(HttpServletRequest request) {
        // 设置JWT过期
        jwtUtil.invalidateJwt(request);
        return R.ok().setMessage("退出成功.");
    }

    /**
     * 刷新过期的token
     * @param refreshToken
     * @return
     */
    @PostMapping("/refresh/token")
    public R<?> refreshToken(String refreshToken) {
        Map<String, String> map;
        // 刷新
        map = jwtUtil.refreshJwt(refreshToken);
        return R.ok(map).setMessage("token刷新成功");
    }

}
