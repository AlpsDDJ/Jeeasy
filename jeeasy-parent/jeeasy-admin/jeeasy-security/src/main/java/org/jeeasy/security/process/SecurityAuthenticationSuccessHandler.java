package org.jeeasy.security.process;

import org.jeeasy.common.core.vo.R;
import org.jeeasy.security.domain.JeeasySecurityUserDetails;
import org.jeeasy.security.service.IJeeasySecurityService;
import org.jeeasy.security.tools.JwtTokenUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Describe: 自定义 Security 用户未登陆处理类
 * Author: 就 眠 仪 式
 * CreateTime: 2019/10/23
 */
//@Component
public class SecurityAuthenticationSuccessHandler<U extends JeeasySecurityUserDetails> implements AuthenticationSuccessHandler {

//    public static final Logger log = LoggerFactory.getLogger(SecurityAccessDeniedHandler.class);

//    @Autowired
    private JwtTokenUtil<U> jwtTokenUtil;
//    @Autowired
    private IJeeasySecurityService<U> securityService;
//    @Resource
//    private LoggingService loggingService;


    public SecurityAuthenticationSuccessHandler(JwtTokenUtil<U> jwtTokenUtil, IJeeasySecurityService<U> securityService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.securityService = securityService;
    }

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

//        R result = R.ok("登陆成功");
        // 将当前用户存入 Session 缓存
        U userDetails = (U) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenUtil.generateToken(userDetails);
        renderToken(httpServletResponse, token);
//        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.getWriter().write(JSON.toJSONString(result));
    }

    /**
     * 渲染返回 token 页面,因为前端页面接收的都是Result对象，故使用application/json返回
     *
     * @param response
     * @throws IOException
     */
    public void renderToken(HttpServletResponse response, String token) throws IOException {
        R.ok(token).responseWrite(response);
    }
}
