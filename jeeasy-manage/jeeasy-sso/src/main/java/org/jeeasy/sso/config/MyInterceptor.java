package org.jeeasy.sso.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO
 *
 * @author wei.yang
 * @date 2022-09-12 13:53
 */
@Slf4j
public class MyInterceptor implements HandlerInterceptor {
    /**
     * 访问控制器方法前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // System.out.println(new Date() + "--preHandle:" + request.getRequestURL());
        log.info("preHandle: {}", request.getRequestURL());
        return true;
    }

    /**
     * 访问控制器方法后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // System.out.println(new Date() + "--postHandle:" + request.getRequestURL());
        log.info("postHandle: {}", request.getRequestURL());
    }

    /**
     * postHandle方法执行完成后执行，一般用于释放资源
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // System.out.println(new Date() + "--afterCompletion:" + request.getRequestURL());
        log.info("afterCompletion: {}", request.getRequestURL());
    }
}
