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
    /**
     * 200: 成功
     */
    SUCCESS(R.SC_OK_200, "成功"),

    /**
     * 401: 未登录
     */
    NO_USER(R.SC_JEEASY_NO_USER, "未登录"),

    /**
     * 403: 暂无权限
     */
    NO_AUTH(R.SC_JEEASY_NO_AUTH, "暂无权限"),

    /**
     * 404: 资源未找到
     */
    NOT_FOUND(R.SC_JEEASY_NOT_FOUND, "资源未找到"),

    /**
     * 405: 请求方法不支持
     */
    NOT_SUPPORTED(R.SC_JEEASY_NOT_SUPPORTED, "请求方法不支持"),

    /**
     * 500: 失败
     */
    ERROR(R.SC_INTERNAL_SERVER_ERROR_500, "失败"),
    ;

    private final Integer code;

    private final String message;

}
