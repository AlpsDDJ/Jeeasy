package org.jeeasy.sso.controller;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.common.core.domain.vo.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author AlpsDDJ
 * @date 2020/11/11
 */
@Slf4j
@RestControllerAdvice
public class AuthControllerAdvice {

    @ExceptionHandler(NotLoginException.class)
    public R<?> handleException(NotLoginException e){
        log.error(e.getMessage(), e);
        return R.error(R.SC_JEEASY_NO_USER, "登录失效").setData(e.getMessage());
    }

    @ExceptionHandler({NotPermissionException.class, NotRoleException.class})
    public R<?> handleException(SaTokenException e){
        log.error(e.getMessage(), e);
        return R.error(R.SC_JEEASY_NO_AUTH, "权限不足").setData(e.getMessage());
    }

//    @ExceptionHandler(NotRoleException.class)
//    public R<?> handleException(NotRoleException e){
//        log.error(e.getMessage(), e);
//        return R.error(R.SC_JEEASY_NO_AUTH, e.getMessage());
//    }

}
