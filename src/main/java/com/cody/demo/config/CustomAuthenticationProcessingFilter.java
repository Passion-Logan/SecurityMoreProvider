package com.cody.demo.config;


import com.cody.demo.security.JwtAuthenticatioToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private static final String AUTH_TYPE = "authType";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String OAUTH_TOKEN_URL = "/home/login";
    private static final String HTTP_METHOD_POST = "POST";

    protected CustomAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher(OAUTH_TOKEN_URL, HTTP_METHOD_POST));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if (!HTTP_METHOD_POST.equals(request.getMethod().toUpperCase())) {
            throw new AuthenticationServiceException("不支持的请求方式: " + request.getMethod());
        }

        AbstractAuthenticationToken token = new JwtAuthenticatioToken(
                request.getParameter(USERNAME), request.getParameter(PASSWORD), request.getParameter(AUTH_TYPE)
        );
//        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        this.setDetails(request, token);


        return this.getAuthenticationManager().authenticate(token);
    }

    protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

}
