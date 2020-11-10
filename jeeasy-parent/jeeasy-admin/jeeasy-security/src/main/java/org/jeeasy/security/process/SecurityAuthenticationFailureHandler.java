package org.jeeasy.security.process;

import com.alibaba.fastjson.JSON;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.security.exception.CaptchaException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 自定义 Security 用户登录失败处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 * */
@Component
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {

    /**
     * 日 志 服 务
     * */
//    @Resource
//    private LoggingService loggingService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setHeader("Content-type","application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        R result = R.error("登陆失败");
        if(e instanceof CaptchaException){
            result.setMessage("验证码有误");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
            return;
        }
        if(e instanceof UsernameNotFoundException){
            result.setMessage("用户名不存在");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
            return;
        }
        if(e instanceof LockedException){
            result.setMessage("用户冻结");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
            return;
        }
        if(e instanceof BadCredentialsException){
            result.setMessage("账户密码不正确");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
            return;
        }
        if(e instanceof DisabledException){
            result.setMessage("用户未启用");
            httpServletResponse.getWriter().write(JSON.toJSONString(result));
            return;
        }
//        Logging logging = new Logging();
//        logging.setId(SequenceUtil.makeStringId());
//        logging.setTitle("登录");
//        logging.setDescription(result.getMsg());
//        logging.setBusinessType(BusinessType.OTHER);
//        logging.setSuccess(false);
//        logging.setLoggingType(LoggingType.LOGIN);
//        loggingService.save(logging);
        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }
}
