package org.jeeasy.auth.service;

import org.jeeasy.auth.domain.JeeasyWebAuthenticationDetails;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AlpsDDJ
 * @version v1.0
 * @description JeeasyAuthenticationDetailsSource
 * @date 2020-11-14
 */
@Component
public class JeeasyAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {
    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new JeeasyWebAuthenticationDetails(request);
    }
}
