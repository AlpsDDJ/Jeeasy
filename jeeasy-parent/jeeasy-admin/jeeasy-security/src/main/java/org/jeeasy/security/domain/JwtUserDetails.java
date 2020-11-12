package org.jeeasy.security.domain;

import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

/**
 * @author AlpsDDJ
 * @date 2020/11/12
 */
public interface JwtUserDetails extends UserDetails {
    Serializable getId();
    String getSalt();
}
