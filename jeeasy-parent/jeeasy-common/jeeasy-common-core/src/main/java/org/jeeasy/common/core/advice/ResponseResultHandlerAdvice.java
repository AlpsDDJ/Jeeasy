package org.jeeasy.common.core.advice;

import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.core.vo.RestCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author AlpsDDJ
 * @date 2020/11/25 11:16
 */
@Slf4j
@ControllerAdvice
public class ResponseResultHandlerAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        log.info("MethodParameter:" + methodParameter);
        log.info("converterType:" + aClass);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof R){
            R<?> result = (R<?>) o;
            if (!RestCode.SUCCESS.getCode().equals(result.getCode())) {
                serverHttpResponse.setStatusCode(HttpStatus.valueOf(result.getCode()));
                return result;
            }
        }
        return o;
    }

//    @Override
//    public R<?> beforeBodyWrite(R<?> r, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        if (null != r && !RestCode.SUCCESS.getCode().equals(r.getCode())) {
//            serverHttpResponse.setStatusCode(HttpStatus.valueOf(r.getCode()));
//        }
//        return r;
//    }
}
