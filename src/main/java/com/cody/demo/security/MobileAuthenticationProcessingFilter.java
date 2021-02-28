package com.cody.demo.security;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MobileAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String USERNAME = "mobile";
    private static final String PASSWORD = "code";
    private static final String OAUTH_TOKEN_URL = "/mobile/login";
    private static final String HTTP_METHOD_POST = "POST";

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(OAUTH_TOKEN_URL,
            HTTP_METHOD_POST);

    private boolean postOnly = true;

    public MobileAuthenticationProcessingFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    public MobileAuthenticationProcessingFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }
        String username = request.getParameter(USERNAME);
        username = (username != null) ? username : "";
        username = username.trim();
        String password = request.getParameter(PASSWORD);
        password = (password != null) ? password : "";

        AbstractAuthenticationToken authRequest = new MobileAuthenticationToken(username, password);
        this.setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }


}
