package org.jeeasy.security.process;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.vo.R;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 自定义 Security 用户未登陆处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Slf4j
@Component
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

//    public static final Logger log = LoggerFactory.getLogger(SecurityAccessDeniedHandler.class);

//    @Resource
//    private LoggingService loggingService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//        Logging logging = new Logging();
//        logging.setId(SequenceUtil.makeStringId());
//        logging.setTitle("登录");
//        logging.setDescription("登录成功");
//        logging.setBusinessType(BusinessType.OTHER);
//        logging.setSuccess(true);
//        logging.setLoggingType(LoggingType.LOGIN);
//        loggingService.save(logging);

        R result = R.ok("登陆成功");
        // 将当前用户存入 Session 缓存
        httpServletRequest.getSession().setAttribute("currentUser",authentication.getPrincipal());
        httpServletResponse.setHeader("Content-type","application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
