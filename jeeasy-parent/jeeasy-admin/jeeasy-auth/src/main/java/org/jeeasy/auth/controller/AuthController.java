package org.jeeasy.auth.controller;

import org.jeeasy.auth.tools.JwtUtil;
import org.jeeasy.auth.vo.AuthUserFormModel;
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

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description AuthController
 * @date 2020-11-14
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

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
        JwtUtil.JwtTokens jwtTokens = jwtUtil.createJwtTokens(authentication, userFormModel.getRememberMe());
        return R.ok(jwtTokens).setMessage("登录成功");
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
        return R.ok().setMessage("注销成功");
    }

    /**
     * 刷新过期的token
     * @param refreshToken
     * @return
     */
    @PostMapping("/refresh/token")
    public R<?> refreshToken(String refreshToken) {
        return R.ok(jwtUtil.refreshJwt(refreshToken)).setMessage("token刷新成功");
    }

}
