package org.jeeasy.common.core.vo;

//import com.alibaba.fastjson.JSON;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * 接口返回数据格式
 *
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Data
@Accessors(chain = true)
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Integer SC_OK_200 = 200;
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /**
     * 访问权限认证未通过 403
     */
    public static final Integer SC_JEEASY_NO_AUTH = 403;
    /**
     * 未登录 401
     */
    public static final Integer SC_JEEASY_NO_USER = 401;
    /**
     * not found 404
     */
    public static final Integer SC_JEEASY_NOT_FOUND = 404;
    /**
     * 请求方法不支持 405
     */
    public static final Integer SC_JEEASY_NOT_SUPPORTED = 405;

    /**
     * 成功标志
     */
//    @ApiModelProperty(value = "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
//    @ApiModelProperty(value = "返回处理消息")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
//    @ApiModelProperty(value = "返回代码")
    private Integer code = 0;

    /**
     * 返回数据对象 data
     */
//    @ApiModelProperty(value = "返回数据对象")
    private T result;

    private long timestamp = System.currentTimeMillis();

    public R<T> success(String message) {
        this.message = message;
        this.code = SC_OK_200;
        this.success = true;
        return this;
    }

    public R<?> faild(String msg) {
        return faild(SC_INTERNAL_SERVER_ERROR_500, msg);
    }

    public R<?> faild(int code, String message) {
        this.message = message;
        this.code = code;
        this.success = false;
        return this;
    }

    public static <T> R<T> ok() {
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(SC_OK_200);
        r.setMessage("成功");
        return r;
    }

    public static <T> R<T> ok(T data) {
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(SC_OK_200);
        r.setResult(data);
        return r;
    }

    public static <T> R<T> ok(String msg, T data) {
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(SC_OK_200);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static R<Object> error(String msg) {
        return error(SC_INTERNAL_SERVER_ERROR_500, msg);
    }

    public static R<Object> error(int code, String msg) {
        R<Object> r = new R<Object>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    /**
     * 无权限访问返回结果
     */
    public static R<Object> noAuth(String msg) {
        return error(SC_JEEASY_NO_AUTH, msg);
    }

    /**
     * 无权限访问返回结果
     */
    public static R<Object> noAuth() {
        return error(SC_JEEASY_NO_AUTH, "权限不足.");
    }

    /**
     * 未登录用户
     */
    public static R<Object> noUser() {
        return error(SC_JEEASY_NO_USER, "未登录");
    }

    /**
     * 未登录用户
     */
    public static R<Object> noUser(String msg) {
        return error(SC_JEEASY_NO_USER, msg);
    }

    /**
     * notFound
     */
    public static R<Object> notFound(String msg) {
        return error(SC_JEEASY_NOT_FOUND, msg);
    }

    /**
     * NotSupported
     */
    public static R<Object> notSupported(String msg) {
        return error(SC_JEEASY_NOT_SUPPORTED, msg);
    }

    public void responseWrite(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(this));
    }

    public static void responseWriteSuccess(Object obj, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(R.ok(obj)));
    }

    public static void responseWriteError(String message, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setHeader("Content-type", "application/json;charset=UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(JSONUtil.toJsonStr(R.error(message)));
    }
}
