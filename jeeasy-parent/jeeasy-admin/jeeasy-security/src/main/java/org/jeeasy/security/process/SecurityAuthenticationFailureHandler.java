package org.jeeasy.security.process;

import org.jeeasy.common.core.vo.R;
import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 自定义 Security 用户登录失败处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
//@Component
public class SecurityAuthenticationFailureHandler<U extends JeeasyBaseSecurityUserDetails> extends SimpleUrlAuthenticationFailureHandler {

    /**
     * 日 志 服 务
     * */
//    @Resource
//    private LoggingService loggingService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//        httpServletResponse.setHeader("Content-type","application/json;charset=UTF-8");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        R result = R.error(e.getMessage());
//        if(e instanceof UsernameNotFoundException){
//            result.setMessage(e.getMessage());
////            httpServletResponse.getWriter().write(JSON.toJSONString(result));
//            return;
//        }
//        if(e instanceof LockedException){
//            result.setMessage(e.getMessage());
////            httpServletResponse.getWriter().write(JSON.toJSONString(result));
//            return;
//        }
//        if(e instanceof BadCredentialsException){
//            result.setMessage(e.getMessage());
////            httpServletResponse.getWriter().write(JSON.toJSONString(result));
//            return;
//        }
//        if(e instanceof DisabledException){
//            result.setMessage(e.getMessage());
////            httpServletResponse.getWriter().write(JSON.toJSONString(result));
//            return;
//        }
//        Logging logging = new Logging();
//        logging.setId(SequenceUtil.makeStringId());
//        logging.setTitle("登录");
//        logging.setDescription(result.getMsg());
//        logging.setBusinessType(BusinessType.OTHER);
//        logging.setSuccess(false);
//        logging.setLoggingType(LoggingType.LOGIN);
//        loggingService.save(logging);
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
        R.responseWriteError(e.getMessage(), httpServletResponse);
    }
}
