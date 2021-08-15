package org.jeeasy.auth.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jeeasy.auth.config.property.SecurityProperty;
import org.jeeasy.common.core.domain.IAuthUser;
import org.jeeasy.auth.domain.JwtClaims;
import org.jeeasy.auth.domain.SecurityUserDetails;
import org.jeeasy.auth.provider.AuthServiceProvider;
import org.jeeasy.auth.tools.JwtUtil;
import org.jeeasy.common.core.exception.JeeasyException;
import org.jeeasy.common.core.tools.Tools;
import org.jeeasy.common.core.domain.vo.R;
import org.jeeasy.common.core.enums.RestCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lirong
 * @ClassName: JwtAuthenticationFilter
 * @Description: Jwt 认证过滤器
 * @date 2019-07-12 9:50
 */
@Component
@Slf4j
@EnableConfigurationProperties({SecurityProperty.class})
public class JwtAuthenticationFilter extends OncePerRequestFilter {

//    @Autowired
//    private SecurityUserService userDetailsService;

    @Autowired
    private AuthServiceProvider authServiceProvider;

    @Autowired
    private JwtUtil jwtUtil;

    @Resource(name = "anonymousUrls")
    private String[] anonymousUrls;

//    @Autowired
//    private CustomConfig customConfig;

//    @Autowired
//    private IApplicationConfig applicationConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 是否为放行的请求
        if (checkIgnores(request)) {
            chain.doFilter(request, response);
            return;
        }

        String jwt = jwtUtil.getJwtFromRequest(request);

        if (StrUtil.isNotBlank(jwt)) {
            try {
//                String username = jwtUtil.getUsernameFromJwt(jwt, false);
                Claims claims = jwtUtil.parseJwt(jwt, false);

                JwtClaims jwtClaims = JwtClaims.build(claims);
                String username = jwtClaims.getUsername();
                String authMethod = jwtClaims.getAuthMethod();

                TimeInterval timer = DateUtil.timer();
                IAuthUser authUser = authServiceProvider.getAuthService(authMethod).getAuthUserByUsername(username);
                if(Tools.isEmpty(authUser)){
                    R.error("账号不存在.").responseWrite(response);
                    return;
                }
                log.info("JwtFilter 获取用户信息耗时: " + timer.interval());
                SecurityUserDetails<?> userDetails = new SecurityUserDetails<>(authUser);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                chain.doFilter(request, response);
            } catch (JeeasyException e) {
                // 全局异常处理无法捕获此处抛出的异常，所以用 responseWrite 返回错误信息
                R.error(e.getCode(), e.getMessage()).responseWrite(response);
            }
        } else {
            R.error(RestCode.NO_USER).responseWrite(response);
        }

    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true - 忽略，false - 不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        AntPathMatcher antPathMatcher = new AntPathMatcher();

        for (String anonymousUrl : anonymousUrls) {
            if (antPathMatcher.match(anonymousUrl, requestURI)) {
                return true;
            }
        }
        return false;
        /*String method = request.getMethod();

        HttpMethod httpMethod = HttpMethod.resolve(method);
        if (ObjectUtil.isNull(httpMethod)) {
            httpMethod = HttpMethod.GET;
        }

        Set<String> ignores = Sets.newHashSet();

        switch (httpMethod) {
            case GET:
                ignores.addAll(customConfig.getIgnores()
                        .getGet());
                break;
            case PUT:
                ignores.addAll(customConfig.getIgnores()
                        .getPut());
                break;
            case HEAD:
                ignores.addAll(customConfig.getIgnores()
                        .getHead());
                break;
            case POST:
                ignores.addAll(customConfig.getIgnores()
                        .getPost());
                break;
            case PATCH:
                ignores.addAll(customConfig.getIgnores()
                        .getPatch());
                break;
            case TRACE:
                ignores.addAll(customConfig.getIgnores()
                        .getTrace());
                break;
            case DELETE:
                ignores.addAll(customConfig.getIgnores()
                        .getDelete());
                break;
            case OPTIONS:
                ignores.addAll(customConfig.getIgnores()
                        .getOptions());
                break;
            default:
                break;
        }

        ignores.addAll(customConfig.getIgnores()
                .getPattern());

        if (CollUtil.isNotEmpty(ignores)) {
            for (String ignore : ignores) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignore, method);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }

        return false;*/
    }
}
