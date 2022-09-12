package org.jeeasy.common.core.annotation.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * IAuthService 接口的实现类徐添加次注解才能被识别为登录认证处理方法
 *
 * @author AlpsDDJ
 * @date 2020/11/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthType {

    /**
     * 处理方法名，与前端登录form参数authType匹配
     * @return 处理方法名
     */
    Type type();

    /**
     * 是否为默认处理方法
     * @return boolean
     */
    boolean izDefault() default false;

    @Getter
    @AllArgsConstructor
    public enum Type {
        SYS_USER("sys_account"),
        FM_MEMBER("fn_member")
        ;

        private final String value;

    }

}
