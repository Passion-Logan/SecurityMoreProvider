package com.cody.demo.config;

import com.cody.demo.dao.BackUserDao;
import com.cody.demo.dao.PcUserDao;
import com.cody.demo.entity.BackUserEntity;
import com.cody.demo.entity.PcUserEntity;
import com.cody.demo.security.JwtAuthenticatioToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

//public class AdminAuthenticationProvider extends DaoAuthenticationProvider {
public class AdminAuthenticationProvider implements AuthenticationProvider {

    /*public AdminAuthenticationProvider(UserDetailsService userDetailsService) {
        setHideUserNotFoundExceptions(false);
        setUserDetailsService(userDetailsService);
        setPasswordEncoder(new BCryptPasswordEncoder());
    }*/

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    PcUserDao pcUserDao;

    @Autowired
    private BackUserDao backUserDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        if (authentication.getPrincipal() == null) {
            throw new RuntimeException("用户名为空");
        }

        JwtAuthenticatioToken token = (JwtAuthenticatioToken) authentication;
        String authType = token.getAuthType();

        if ("PC".equals(authType)) {
            PcUserEntity byPhone = pcUserDao.findByPhone((String) token.getPrincipal());
            if (byPhone == null) {
                throw new UsernameNotFoundException("用户不存在");
            }
        } else if ("BACK".equals(authType)) {
            BackUserEntity byPhone = backUserDao.findByPhone((String) token.getPrincipal());
            if (byPhone == null) {
                throw new UsernameNotFoundException("用户不存在");
            }

        } else {
            // 其它方式
        }

        return authentication;

        // 可以在此处覆写整个登录认证逻辑
//        return super.authenticate(authentication);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticatioToken.class.isAssignableFrom(authentication));
    }

    /*@Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        // 可以在此处覆写密码验证逻辑
        super.additionalAuthenticationChecks(userDetails, authentication);
    }*/

}
