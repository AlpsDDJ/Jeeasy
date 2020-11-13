package org.jeeasy.security.process;

import org.jeeasy.common.core.vo.R;
import org.jeeasy.security.domain.JeeasySecurityUserDetails;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 自定义 Security 用户暂无权限处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
//@Slf4j
//@Component
public class SecurityAccessDeniedHandler<U extends JeeasySecurityUserDetails> implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        R.noAuth("暂无权限").responseWrite(httpServletResponse);
//        R<?> result = R.noAuth("暂无权限");
//        httpServletResponse.setHeader("Content-type","application/json;charset=UTF-8");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
