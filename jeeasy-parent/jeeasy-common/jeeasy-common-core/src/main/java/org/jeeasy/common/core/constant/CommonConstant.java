package org.jeeasy.common.core.constant;

/**
 * @author AlpsDDJ
 * @date 2020/11/9
 */
public class CommonConstant {
    public static final String X_ACCESS_TOKEN = "JEEASY-ACCESS-TOKEN";
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String CACHE_SYS_USER_KEY = "sys:user:";
//    public static final String SYS_USER_JWT_KEY_PREFIX = "jwt:sys:";
//    public static final String SYS_USER_JWT_SUBJECT_RACE_KEY_PREFIX = "jwt:sys:subject:race:";

    /**
     * JWT-token 在 Redis 中保存的key前缀
     */
    public static final String REDIS_JWT_TOKEN_KEY_PREFIX = "security:jwt:token:";

    /**
     * JWT-refresh-token 在 Redis 中保存的key前缀
     */
    public static final String REDIS_JWT_REFRESH_TOKEN_KEY_PREFIX = "security:jwt:refresh-token:";

}
