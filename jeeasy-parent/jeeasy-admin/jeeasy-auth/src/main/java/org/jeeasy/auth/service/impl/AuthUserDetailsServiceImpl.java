//package org.jeeasy.auth.service.impl;
//
//import org.jeeasy.auth.domain.IAuthUser;
//import org.jeeasy.auth.service.IAuthService;
//import org.jeeasy.auth.service.IAuthUserDetailsService;
//import org.jeeasy.common.core.tools.Tools;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicReference;
//
///**
// * @author AlpsDDJ
// * @version v1.0
// * @description AuthUserDetailsServiceImpl
// * @date 2020-11-14
// */
//@Service
//public class AuthUserDetailsServiceImpl implements IAuthUserDetailsService {
//
//    @Autowired
//    private List<IAuthService<?>> authServiceList;
//
////    private IAuthService<?> authService;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//
//        String authUserClazz = s.split("@")[1];
//        String username = s.split("@")[0];
//        try {
//            Class clazz = Class.forName(authUserClazz);
//            IAuthService authService = getAuthService(clazz);
//            if(Tools.isEmpty(authService)){
//                authService = getAuthService()
//            }
//            return authService.getUserByUsername(username).toAuthUserDetails();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//            throw new BadCredentialsException("无法匹配认证方法.");
//        }
//    }
//
//    @Override
//    public <T extends IAuthUser> IAuthService<T> getAuthService(Class<T> clazz) {
//        AtomicReference<IAuthService<T>> authService = new AtomicReference<>();
//        authServiceList.forEach(s -> {
//            if (s.getAuthMethod().equals(clazz.getName())) {
//                authService.set((IAuthService<T>) s);
//            }
//        });
//        return authService.get();
//    }
//
//    @Override
//    public IAuthService<?> getAuthService(String method) {
//        return null;
//    }
//}
