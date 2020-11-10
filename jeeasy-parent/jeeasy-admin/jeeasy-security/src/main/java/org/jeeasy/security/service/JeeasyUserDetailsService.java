package org.jeeasy.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JeeasyUserDetailsService<S extends UserDetailsService, T extends UserDetails> implements UserDetailsService {
    protected S service;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return service.loadUserByUsername(s);
    }
}
