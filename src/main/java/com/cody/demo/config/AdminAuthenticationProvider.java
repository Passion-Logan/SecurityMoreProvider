package com.cody.demo.config;

import com.cody.demo.dao.PcUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    PcUserDetailsServiceImpl userDetailsService;

    @Autowired
    PcUserDao pcUserDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);


        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
