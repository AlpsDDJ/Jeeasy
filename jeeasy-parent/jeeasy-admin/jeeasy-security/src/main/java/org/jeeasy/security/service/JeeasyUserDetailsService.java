package org.jeeasy.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author Alps
 */
@Component
public interface JeeasyUserDetailsService<S extends UserDetailsService, T extends UserDetails> extends UserDetailsService {

    S getService();

    @Override
    default T loadUserByUsername(String s) throws UsernameNotFoundException {
        return (T)getService().loadUserByUsername(s);
    }
}
