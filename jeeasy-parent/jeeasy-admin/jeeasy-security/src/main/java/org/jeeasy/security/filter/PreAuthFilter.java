package org.jeeasy.security.filter;

import cn.hutool.core.util.NumberUtil;
import org.jeeasy.common.cache.tools.RedisUtil;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
import org.jeeasy.security.tools.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author AlpsDDJ
 * @date 2020/11/13
 */
@Component
public class PreAuthFilter extends BasicAuthenticationFilter {
    @Autowired
    private RedisUtil<String, Object> redisUtil;
    @Autowired
    private JwtTokenUtil<JeeasyBaseSecurityUserDetails> tokenUtil;


    public PreAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * description: 从request的header部分读取Token
     *
     * @param request
     * @param response
     * @param chain
     * @return void
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String tokenHeader = tokenUtil.getTokenFromRequest(request);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader, response));
        super.doFilterInternal(request, response, chain);
    }

    /**
     * description: 读取Token信息，创建UsernamePasswordAuthenticationToken对象
     *
     * @param tokenHeader
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader, HttpServletResponse response) throws IOException {
        //解析Token时将“Bearer ”前缀去掉

        try {
            String[] splitToken = tokenHeader.split("\\$");
            String token = splitToken[0];
            Integer number = NumberUtil.parseInt(splitToken[1]);
            tokenUtil.refreshToken(token, number);
        }catch (Exception e){
            R.error("token无效").responseWrite(response);
        }

        return null;
    }

}
