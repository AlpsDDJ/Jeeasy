package org.jeeasy.common.core.constant;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public class CommonConstant {
    public static final String X_ACCESS_TOKEN = "JEEASY-ACCESS-TOKEN";
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
}
