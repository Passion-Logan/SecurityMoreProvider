package com.cody.demo.security;

import com.cody.demo.dao.BackUserDao;
import com.cody.demo.dao.PcUserDao;
import com.cody.demo.entity.BackUserEntity;
import com.cody.demo.entity.PcUserEntity;
import com.cody.demo.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private PcUserDao pcUserDao;

    @Autowired
    private BackUserDao backUserDao;

    @Override
    public Authentication authenticate(Authentication authentication) {

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

        return new JwtAuthenticatioToken(authentication.getPrincipal(), authentication.getCredentials(), JwtTokenUtils.generateToken(authentication));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticatioToken.class.isAssignableFrom(authentication));
    }

}
