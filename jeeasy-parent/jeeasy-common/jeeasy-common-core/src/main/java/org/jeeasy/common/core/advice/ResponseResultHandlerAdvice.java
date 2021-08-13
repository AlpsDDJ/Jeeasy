//package org.jeeasy.common.core.advice;
//
//import lombok.extern.slf4j.Slf4j;
//import org.jeeasy.common.core.vo.R;
//import org.springframework.core.MethodParameter;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
//
///**
// * @author AlpsDDJ
// * @date 2020/11/25 11:16
// */
//@Slf4j
//@ControllerAdvice
//public class ResponseResultHandlerAdvice implements ResponseBodyAdvice<Object> {
//    @Override
//    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
//        log.info("MethodParameter:" + methodParameter);
//        log.info("converterType:" + aClass);
//        return true;
//    }
//
//    @Override
//    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
//        log.info(serverHttpRequest.getURI().getPath());
//        if(o instanceof R){
//            R<?> result = (R<?>) o;
//            if (!result.isSuccess()) {
//                try {
//                    HttpStatus httpStatus = HttpStatus.valueOf(result.getCode());
//                    serverHttpResponse.setStatusCode(httpStatus);
//                    return result;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return o;
//    }
//
////    @Override
////    public R<?> beforeBodyWrite(R<?> r, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
////        if (null != r && !RestCode.SUCCESS.getCode().equals(r.getCode())) {
////            serverHttpResponse.setStatusCode(HttpStatus.valueOf(r.getCode()));
////        }
////        return r;
////    }
//}
