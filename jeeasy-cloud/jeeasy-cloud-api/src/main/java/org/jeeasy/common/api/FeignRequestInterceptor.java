package org.jeeasy.common.api;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.stp.StpUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.config.constant.CommonConstant;
import org.springframework.context.annotation.Configuration;

/**
 * openfeign 调用携带 token
 *
 * @author wei.yang
 * @date 2023-01-02 18:48
 */
@Configuration
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        try {
            template.header(CommonConstant.X_ACCESS_TOKEN, StpUtil.getTokenValue());
        } catch (NotLoginException e){
            log.warn("openfeign[setToken] - ", e);
        }
    }
}
