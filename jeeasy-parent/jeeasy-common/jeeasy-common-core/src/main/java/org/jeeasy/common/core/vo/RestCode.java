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
    SUCCESS(200, "成功"),
    ERROR(500, "失败"),
    NO_AUTH(403, "暂无权限"),
    NO_USER(401, "未登录"),
    JWT_TOKEN_ERROR(600, "TOKEN错误")
    ;

    private Integer code;

    private String message;

}
