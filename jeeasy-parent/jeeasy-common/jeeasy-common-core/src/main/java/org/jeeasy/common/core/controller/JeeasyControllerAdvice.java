package org.jeeasy.common.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.vo.R;
import org.jeeasy.common.core.vo.RestCode;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author AlpsDDJ
 * @date 2020/11/11
 */
@Slf4j
@RestControllerAdvice
public class JeeasyControllerAdvice implements ResponseBodyAdvice<Object> {

    @ExceptionHandler(Exception.class)
    public R<?> handleException(Exception e){
        log.error(e.getMessage(), e);
        return R.error("操作失败，"+e.getMessage());
    }

    @ExceptionHandler(JeeasyException.class)
    public R<?> jeeasyExceptionHandler(JeeasyException ex) {
        log.error(ex.getMessage());
        return R.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public R<?> handlerNoFoundException(NoHandlerFoundException e) {
        log.error(e.getMessage(), e);
        throw new JeeasyException(RestCode.NOT_FOUND);
//        return R.notFound("路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R<?> handleDuplicateKeyException(DuplicateKeyException e){
        log.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

//    @ExceptionHandler({UnauthorizedException.class, AuthorizationException.class})
//    public R<?> handleAuthorizationException(AuthorizationException e){
//        log.error(e.getMessage(), e);
//        return Result.noauth("没有权限，请联系管理员授权");
//    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R<?> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        StringBuffer sb = new StringBuffer();
        sb.append("不支持");
        sb.append(e.getMethod());
        sb.append("请求方法，");
        sb.append("支持以下");
        String [] methods = e.getSupportedMethods();
        if(methods!=null){
            for(String str:methods){
                sb.append(str);
                sb.append("、");
            }
        }
        log.error(sb.toString(), e);
        throw new JeeasyException(RestCode.NOT_SUPPORTED, sb.toString());
//        return R.notSupported(sb.toString());
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public R<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return R.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public R<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        return R.error("字段太长,超出数据库字段的长度");
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        log.info("MethodParameter:" + methodParameter);
        log.info("converterType:" + aClass);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        log.info(serverHttpRequest.getURI().getPath());
        if(o instanceof R){
            R<?> result = (R<?>) o;
            if (!result.isSuccess()) {
                try {
                    HttpStatus httpStatus = HttpStatus.valueOf(result.getCode());
                    serverHttpResponse.setStatusCode(httpStatus);
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return o;
    }

//    @ExceptionHandler(PoolException.class)
//    public R<?> handlePoolException(PoolException e) {
//        log.error(e.getMessage(), e);
//        return R.error("Redis 连接异常!");
//    }

}
