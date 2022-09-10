package org.jeeasy.auth.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description Permission
 * @date 2020-11-14
 */
@Data
public class Permission implements Serializable {
    /**
     * 权限码
     */
    private String code;
    /**
     * 请求路径
     */
    private String path;
    /**
     * 请求方法
     * -- get post put delete
     */
    private String method;
}
