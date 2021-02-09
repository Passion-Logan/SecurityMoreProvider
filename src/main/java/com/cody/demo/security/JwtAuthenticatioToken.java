package com.cody.demo.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @Description: 自定义令牌对象
 */
public class JwtAuthenticatioToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    private String token;

    private String authType;

    public JwtAuthenticatioToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public JwtAuthenticatioToken(Object principal, Object credentials, String authType) {
        super(principal, credentials);
        this.authType = authType;
    }

    public JwtAuthenticatioToken(Object principal, Object credentials, String authType, String token) {
        super(principal, credentials);
        this.token = token;
        this.authType = authType;
    }

    public JwtAuthenticatioToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String token) {
        super(principal, credentials, authorities);
        this.token = token;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

}

