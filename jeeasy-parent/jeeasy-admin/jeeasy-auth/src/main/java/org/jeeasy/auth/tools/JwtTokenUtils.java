package org.jeeasy.auth.tools;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.jeeasy.auth.domain.SecurityUserDetails;
import org.jeeasy.common.core.constant.CommonConstant;
import org.jeeasy.common.core.exception.JeeasyException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

/**
 * Created by echisan on 2018/6/23
 */
public class JwtTokenUtils {

    public static final String TOKEN_HEADER = CommonConstant.X_ACCESS_TOKEN;
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "jwtsecretdemo";
    private static final String ISS = "echisan";

    // 角色的key
    private static final String ROLE_CLAIMS = "roles";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    // 创建token
    public static String createToken(String username, Set<String> role, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, role);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    // 创建token
    public static String createToken(SecurityUserDetails<?> userDetails, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>();
        map.put(ROLE_CLAIMS, userDetails.getAuthorities());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(userDetails.getIssuer())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    // 获取用户角色
    public static List<String> getUserRole(String token) {
        Claims tokenBody = getTokenBody(token);
        return (List<String>) tokenBody.get(ROLE_CLAIMS);
    }

    // 是否已过期
    public static boolean isExpiration(String token) {
        try {
            return getTokenBody(token).getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean verity(String tokenHeader){
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        return !JwtTokenUtils.isExpiration(token);
    }

    // 这里从token中获取用户信息并新建一个token
    public static UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) throws JeeasyException {
        String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        boolean expiration = JwtTokenUtils.isExpiration(token);
        if (expiration) {
            throw new JeeasyException("Token超时.");
        } else {
            String username = JwtTokenUtils.getUsername(token);
            List<String> roles = JwtTokenUtils.getUserRole(token);
            Set<SimpleGrantedAuthority> simpleGrantedAuthoritySet = new HashSet<>();
            roles.forEach(role -> {
                simpleGrantedAuthoritySet.add(new SimpleGrantedAuthority(role));
            });
            if (username != null) {
                return new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthoritySet);
            }
        }
        return null;
    }
}
