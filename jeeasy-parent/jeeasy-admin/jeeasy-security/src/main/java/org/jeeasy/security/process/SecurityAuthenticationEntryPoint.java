package org.jeeasy.security.process;

import org.jeeasy.common.core.vo.R;
import org.jeeasy.security.domain.JeeasySecurityUserDetails;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 自定义 Security 用户未登陆处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
//@Component
public class SecurityAuthenticationEntryPoint<U extends JeeasySecurityUserDetails> implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        R<?> result = R.noUser("未登录.");
//        httpServletResponse.setHeader("Content-type","application/json;charset=UTF-8");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
        R.noUser("未登录.").responseWrite(httpServletResponse);
    }
}
