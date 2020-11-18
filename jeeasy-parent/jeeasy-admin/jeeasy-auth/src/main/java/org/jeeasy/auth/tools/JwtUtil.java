package org.jeeasy.auth.tools;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.auth.config.JwtConfig;
import org.jeeasy.auth.domain.SecurityUserDetails;
import org.jeeasy.common.core.constant.CommonConstant;
import org.jeeasy.common.core.exception.JeeasyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * jwt工具类
 *
 * @author daiyp
 * @date 2018-9-26
 */
@EnableConfigurationProperties(JwtConfig.class)
@Configuration
@Slf4j
public class JwtUtil {

    private static final String CLAIM_ROLES_KEY = "role";
    private static final String CLAIM_PERMISSIONS_KEY = "permission";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 创建JWT
     *
     * @param authentication 用户认证信息
     * @param rememberMe     记住我
     * @return JWT
     */
    public String createJwt(Authentication authentication, Boolean rememberMe, Boolean isRefresh) {
        SecurityUserDetails<?> user = (SecurityUserDetails<?>) authentication.getPrincipal();
        return createJwt(isRefresh, rememberMe, user.getId(), user.getUsername(), user.getRoles(), user.getPermissions());
    }

    /**
     * 创建JWT
     *
     * @param id          用户id
     * @param subject     用户名
     * @param roles       用户角色
     * @param authorities 用户权限
     * @return JWT
     */
    public String createJwt(Boolean isRefresh,
                            Boolean rememberMe,
                            String id,
                            String subject,
                            Set<String> roles,
                            Set<String> permissions) {
        Date now = new Date();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtConfig.getSecret())
                .claim(CLAIM_ROLES_KEY, roles)
                // .claim("perms", menus)
                .claim(CLAIM_PERMISSIONS_KEY, permissions);

        // 设置过期时间
        Long ttl = rememberMe ? jwtConfig.getRemember() : jwtConfig.getTtl();
        String redisKey;
        if (isRefresh) {
            ttl *= 3;
            redisKey = CommonConstant.REDIS_JWT_REFRESH_TOKEN_KEY_PREFIX + subject;
        } else {
            redisKey = CommonConstant.REDIS_JWT_TOKEN_KEY_PREFIX + subject;
        }
        if (ttl > 0) {
            builder.setExpiration(DateUtil.offsetMillisecond(now, ttl.intValue()));
        }

        String jwt = builder.compact();
        // 将生成的JWT保存至Redis
        redisTemplate.opsForValue().set(redisKey, jwt, ttl, TimeUnit.MILLISECONDS);
        return jwt;
    }

    /**
     * 解析JWT
     *
     * @param jwt JWT
     * @return {@link Claims}
     */
    public Claims parseJwt(String jwt, Boolean isRefresh) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSecret())
                    .parseClaimsJws(jwt)
                    .getBody();

            String username = claims.getSubject();
            String redisKey = (isRefresh ? CommonConstant.REDIS_JWT_REFRESH_TOKEN_KEY_PREFIX : CommonConstant.REDIS_JWT_TOKEN_KEY_PREFIX)
                    + username;

            // 校验redis中的JWT是否存在
            Long expire = redisTemplate.getExpire(redisKey, TimeUnit.MILLISECONDS);
            if (Objects.isNull(expire) || expire <= 0) {
                throw new JeeasyException("token 已过期");
            }

            // 校验redis中的JWT是否与当前的一致，不一致则代表用户已注销/用户在不同设备登录，均代表JWT已过期
            String redisToken = (String) redisTemplate.opsForValue().get(redisKey);
            if (!StrUtil.equals(jwt, redisToken)) {
                throw new JeeasyException("当前用户已在别处登录.");
            }
            return claims;
        } catch (ExpiredJwtException e) {
            log.error("Token 已过期");
            throw new JeeasyException("用户 刷新令牌过期");
        } catch (UnsupportedJwtException e) {
            log.error("不支持的 Token");
            throw new JeeasyException("token 解析失败: 不支持.");
        } catch (MalformedJwtException e) {
            log.error("Token 无效");
            throw new JeeasyException("token 解析失败: 无效.");
        } catch (IllegalArgumentException e) {
            log.error("Token 参数不存在");
            throw new JeeasyException("token 解析失败: 参数不存在.");
        }
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJwt(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJwt(jwt, false);
        // 从redis中清除JWT
        redisTemplate.delete(CommonConstant.REDIS_JWT_REFRESH_TOKEN_KEY_PREFIX + username);
        redisTemplate.delete(CommonConstant.REDIS_JWT_TOKEN_KEY_PREFIX + username);
    }

    /**
     * 从 request 的 header 中获取 JWT
     *
     * @param request 请求
     * @return JWT
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith(CommonConstant.TOKEN_PREFIX)) {
            return bearerToken.replace(CommonConstant.TOKEN_PREFIX, "");
        }
        return null;
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJwt(String jwt, Boolean isRefresh) {
        Claims claims = parseJwt(jwt, isRefresh);
        return claims.getSubject();
    }

    public Map<String, String> refreshJWT(String token) {
        Claims claims = parseJwt(token, true);
        // 获取签发时间
        Date lastTime = claims.getExpiration();
        // 1. 判断refreshToken是否过期
        if (!new Date().before(lastTime)) {
            throw new JeeasyException("token 已过期");
        }
        // 2. 在redis中删除之前的token和refreshToken
        String username = claims.getSubject();
        // redisTemplate.delete(Constant.REDIS_JWT_REFRESH_TOKEN_KEY_PREFIX + username);
        // redisTemplate.delete(Constant.REDIS_JWT_TOKEN_KEY_PREFIX + username);
        // 3. 创建新的token和refreshToken并存入redis
        String jwtToken = createJwt(false, false, claims.getId(), username,
                (Set<String>) claims.get(CLAIM_ROLES_KEY), (Set<String>) claims.get(CLAIM_PERMISSIONS_KEY));
        String refreshJwtToken = createJwt(true, false, claims.getId(), username,
                (Set<String>) claims.get(CLAIM_ROLES_KEY), (Set<String>) claims.get(CLAIM_PERMISSIONS_KEY));
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        map.put("refreshToken", refreshJwtToken);
        return map;
    }

    /**
     * 功能：生成 jwt token<br/>
     *
     * @param name           实例名
     * @param param          需要保存的参数
     * @param secret         秘钥
     * @param expirationtime 过期时间(5分钟 5*60*1000)
     * @return
     */
    public static String sign(String name, Map<String, Object> param, String secret, Long expirationtime) {
        String JWT = Jwts.builder()
                .setClaims(param)
                .setSubject(name)
                .setExpiration(new Date(System.currentTimeMillis() + expirationtime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return JWT;
    }

    /**
     * 功能：解密 jwt<br/>
     *
     * @param JWT    token字符串
     * @param secret 秘钥
     * @return
     * @throws
     */
    public static Claims verify(String JWT, String secret) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(JWT)
                .getBody();
        return claims;
    }

    public static Object getValueFromToken(String jwt, String key, String secret) {
        return verify(jwt, secret).get(key);
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVf6LaF57qn566h55CG5ZGYIl0sImV4cCI6MTU2MjgzMjU5Nn0.eH57RyBH-Kwct6GmLyHDN9TjRTF02wDNma-NKZx711w";
        Claims claims = verify(token, "janche");
        Object value = getValueFromToken(token, CLAIM_ROLES_KEY, "janche");
        System.out.println(value);
        System.out.println(claims);
    }

}
