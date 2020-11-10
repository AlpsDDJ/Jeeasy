package org.jeeasy.common.core.vo;

import lombok.Data;
import org.jeeasy.common.core.constant.CommonConstant;

import java.io.Serializable;

/**
 * 接口返回数据格式
 *
 * @author AlpsDDJ
 * @date 2020/11/9
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

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
        this.code = CommonConstant.SC_OK_200;
        this.success = true;
        return this;
    }

    public R<?> faild(String msg) {
        return faild(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
    }

    public R<?> faild(int code, String message) {
        this.message = message;
        this.code = code;
        this.success = false;
        return this;
    }

    public static<T> R<T> ok() {
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage("成功");
        return r;
    }

    public static<T> R<T> ok(T data) {
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setResult(data);
        return r;
    }

    public static<T> R<T> ok(String msg, T data) {
        R<T> r = new R<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage(msg);
        r.setResult(data);
        return r;
    }

    public static R<Object> error(String msg) {
        return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
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
        return error(CommonConstant.SC_JEEASY_NO_AUTH, msg);
    }

    /**
     * 未登录用户
     */
    public static R<Object> noUser(String msg) {
        return error(CommonConstant.SC_JEEASY_NO_USER, msg);
    }
}
