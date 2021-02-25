package com.cody.demo.security;


import com.cody.demo.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MobileAuthenticationProvider implements AuthenticationProvider {

    /**
     * 注入自定义的service
     */
    @Autowired
    private MobileUserDetailService mobileUserDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        JwtUserDetails mobileDetails = (JwtUserDetails) mobileUserDetailService.loadUserByUsername(username);

        String cachePwd = mobileDetails.getPassword();

        // 比较表单输入的验证码
        if (!cachePwd.equals(password)) {
            throw new BadCredentialsException("验证码错误");
        }

        /*try {
            // 登录认证等
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage());
        }*/

        MobileAuthenticationToken token = new MobileAuthenticationToken(username, password, mobileDetails.getAuthorities(), JwtTokenUtils.generateToken(authentication));
        token.setToken(JwtTokenUtils.generateToken(authentication));

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        /**
         * providerManager会遍历所有securityconfig中注册的provider集合
         * 根据此方法返回true或false来决定由哪个provider
         * 去校验请求过来的authentication
         */
        return (MobileAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
