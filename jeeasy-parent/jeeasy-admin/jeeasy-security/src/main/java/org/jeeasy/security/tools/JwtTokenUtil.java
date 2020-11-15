package org.jeeasy.security.tools;

import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jeeasy.common.cache.tools.RedisUtil;
import org.jeeasy.common.core.constant.CommonConstant;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.security.domain.JeeasyBaseSecurityUserDetails;
import org.jeeasy.security.domain.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author AlpsDDJ
 * @date 2020/11/12
 */
@Component
public class JwtTokenUtil<U extends JeeasyBaseSecurityUserDetails> implements Serializable {
    private static final long serialVersionUID = -4324967L;

    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";
    private static final String CLAIM_KEY_ROLES = "roles";

    @Autowired
    private RedisUtil<Serializable, Object> redisUtil;

    @Value("${jeeasy.security.jwt.secret}")
    private String secret;

    @Value("${jeeasy.security.jwt.expiration}")
    private Long expiration;

    /**
     * 从数据声明生成令牌
     *
     * @param claims 数据声明
     * @return 令牌
     */
    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
//        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
        String token = Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
//        redisTemplate.set("", token);
        return token;
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (token == null) {
            token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        }
        return token;
    }

    public Serializable getUsernameByRequest(HttpServletRequest request) {
        return getUsernameFromToken(getTokenFromRequest(request));
    }

    /**
     * 生成签名,5min后过期
     *
     * @param username 用户名
     * @return 加密的token
     */
    public String sign(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATED, LocalDateTime.now());
        // 附带username信息
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 生成令牌
     *
     * @param userDetails 用户
     * @return 令牌
     */
    public String generateToken(U userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put(Claims.SUBJECT, userDetails.username());
        claims.put(Claims.ISSUED_AT, new Date());
        return generateToken(claims);
    }

    /**
     * 从令牌中获取用户名
     *
     * @param token 令牌
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 验证令牌
     *
     * @param token       令牌
     * @param userDetails 用户
     * @return 是否有效
     */
    public Boolean validateToken(String token, U userDetails) {
        try {
            String username = getUsernameFromToken(token);
            return (username.equals(userDetails.username()) && !isTokenExpired(token));
        } catch (Exception exception) {
            return false;
        }
    }

    public String getRedisKey(String username, Integer number) {
        return StrUtil.format("{}{}${}", CommonConstant.SYS_USER_JWT_KEY_PREFIX, username, number);
    }

    public String getRedisKey(String username) {
        return StrUtil.format("{}{}", CommonConstant.SYS_USER_JWT_KEY_PREFIX, username);
    }

    public Integer getUserLoginNumber(String username, Integer max) {
        if (max == 0) {
            int i = 0;
            Object o = redisUtil.get(getRedisKey(username, i++));
            while (Tools.isNotEmpty(o)) {
                o = redisUtil.get(getRedisKey(username, i++));
            }
            return i - 1;
        } else {
            for (int i = 0; i < max; i++) {
                Object o = redisUtil.get(getRedisKey(username, i));
                if (Tools.isEmpty(o)) {
                    return i;
                }
            }
            throw new JeeasyException("同时登录数超限.");
        }
    }

    /**
     * 保存 jwtUser 到 redis
     *
     * @param jwtUserDetails
     */
    public void saveUserToRedis(JwtUserDetails jwtUserDetails) {
        redisUtil.set(getRedisKey(jwtUserDetails.getUsername(), jwtUserDetails.getNumber()), jwtUserDetails, expiration);
    }

    /**
     * 保存 userDetails 到 redis
     *
     * @param userDetails
     */
    public void saveUserToRedis(JeeasyBaseSecurityUserDetails userDetails) {
        redisUtil.set(getRedisKey(userDetails.username()), userDetails, expiration * 2);
    }

    /**
     * 刷新令牌
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put(Claims.ISSUED_AT, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }

    public void refreshToken(String userName, Integer number) {
        redisUtil.expire(getRedisKey(userName, number), expiration);
//        redisUtil.expire(userKey, null);

    }

}