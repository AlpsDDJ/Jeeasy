package org.jeeasy.common.core.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description RestCode
 * @date 2020-11-21
 */
@Getter
@AllArgsConstructor
public enum RestCode {
    SUCCESS(R.SC_OK_200, "成功"),
    ERROR(R.SC_INTERNAL_SERVER_ERROR_500, "失败"),
    NO_AUTH(R.SC_JEEASY_NO_AUTH, "暂无权限"),
    NO_USER(R.SC_JEEASY_NO_USER, "未登录"),
    JWT_TOKEN_ERROR(600, "TOKEN错误"),
    NOT_SUPPORTED(R.SC_JEEASY_NOT_SUPPORTED, "请求方法不支持"),
    NOT_FOUND(R.SC_JEEASY_NOT_FOUND, "资源未找到"),
    ;

    private final Integer code;

    private final String message;

}
