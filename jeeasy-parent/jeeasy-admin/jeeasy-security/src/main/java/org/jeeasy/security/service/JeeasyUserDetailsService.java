//package org.jeeasy.security.service;
//
//import org.jeeasy.security.domain.JwtUserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//
///**
// * @author Alps
// */
//public interface JeeasyUserDetailsService<S extends UserDetailsService, T extends JwtUserDetails> extends UserDetailsService {
//
//    /**
//     * 取得用户service
//     * @return
//     */
//    S getService();
//
//    @Override
//    default T loadUserByUsername(String s) throws UsernameNotFoundException {
//        return (T)getService().loadUserByUsername(s);
//    }
//}
