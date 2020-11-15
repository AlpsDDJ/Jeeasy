//package org.jeeasy.auth.controller;
//
//import org.jeeasy.auth.domain.AuthUserFormModel;
//import org.jeeasy.common.core.vo.R;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
///**
// * @author AlpsDDJ
// * @version v1.0
// * @description AuthController
// * @date 2020-11-14
// */
//@RestController("/auth")
//public class AuthController {
//
////    @Autowired
////    List<IAuthService<?>> authServiceList;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @PostMapping("/login")
//    public R<?> auth(AuthUserFormModel userFormModel) {
//
//        // 内部登录请求
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userFormModel.getUsername(), userFormModel.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
//        // 验证
//        Authentication auth = authenticationManager.authenticate(authRequest);
//        SecurityContextHolder.getContext().setAuthentication(auth);
//
//        return R.ok();
//    }
//
//}
