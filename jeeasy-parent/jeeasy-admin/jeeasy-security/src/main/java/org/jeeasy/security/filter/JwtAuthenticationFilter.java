//package org.jeeasy.security.filter;
//
//import org.jeeasy.common.core.tools.Tools;
//import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
//import org.jeeasy.security.service.IJeeasySecurityService;
//import org.jeeasy.security.tools.JwtTokenUtil;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * @author AlpsDDJ
// * @date 2020/11/12
// */
////@Component
//public class JwtAuthenticationFilter<U extends JeeasyBaseSecurityUserDetails> extends OncePerRequestFilter {
//
//
////    @Autowired
//    private JwtTokenUtil<U> jwtTokenUtil;
//
////    @Autowired
//    private IJeeasySecurityService<U> securityService;
//
//    public JwtAuthenticationFilter(JwtTokenUtil<U> jwtTokenUtil, IJeeasySecurityService<U> securityService) {
//        this.jwtTokenUtil = jwtTokenUtil;
//        this.securityService = securityService;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        String token = jwtTokenUtil.getTokenFromRequest(request);
//        if (Tools.isNotEmpty(token)) {
//            String username = jwtTokenUtil.getUsernameFromToken(token);
//            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                U userDetails = securityService.getUserByUsername(username);
//                if (jwtTokenUtil.validateToken(token, userDetails)) {
//                    // 将用户信息存入 authentication，方便后续校验
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities());
//                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                    // 将 authentication 存入 ThreadLocal，方便后续获取用户信息
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
//        chain.doFilter(request, response);
//    }
//}
