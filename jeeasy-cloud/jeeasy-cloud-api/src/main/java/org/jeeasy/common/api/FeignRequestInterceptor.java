package org.jeeasy.common.api;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Enumeration;

/**
 * openfeign 调用携带 token
 *
 * @author wei.yang
 * @date 2023-01-02 18:48
 */
//@Configuration
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
            String pathInfo = request.getRequestURI();
            log.info("feign interceptor request:{}", pathInfo);
            Enumeration<String> headerNames = request.getHeaderNames();
            if (headerNames != null) {
                while (headerNames.hasMoreElements()) {
                    String name = headerNames.nextElement();
                    String values = request.getHeader(name);
                    // 跳过 content-length
                    if ("content-length".equalsIgnoreCase(name)) {
                        continue;
                    }
                    template.header(name, values);
                }
            } else {
                log.info("feign interceptor error header:{}", template);
            }
        }

        //try {
        //    template.header(CommonConstant.X_ACCESS_TOKEN, StpUtil.getTokenValue());
        //} catch (NotLoginException e) {
        //    log.warn("openfeign[setToken] - ", e);
        //}
    }
}
