package com.cody.demo.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * 安全用户模型
 */
public class JwtUserDetails extends User {


    public JwtUserDetails(String username, String password) {
        super(username, password, true, true, true, true, null);
    }

    public JwtUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public JwtUserDetails(String username, String password, Boolean enabled, List<GrantedAuthority> authorities) {
        this(username, password, enabled, true, true, true, authorities);
    }
}
