package org.jeeasy.security.process;

import org.jeeasy.common.core.vo.R;
import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 自定义 Security 注销成功处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
//@Component
public class SecurityLogoutSuccessHandler<U extends JeeasyBaseSecurityUserDetails> implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        R.ok("注销成功").responseWrite(httpServletResponse);
//        R result = R.ok("注销成功");
//        SecurityContextHolder.clearContext();
//        httpServletResponse.setHeader("Content-type","application/json;charset=UTF-8");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
