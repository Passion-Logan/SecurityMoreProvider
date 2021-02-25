package com.cody.demo.security;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MobileUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String password = "";
        Boolean flag = false;
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 根据用户名查询数据库中的用户,判断是否存在;获取缓存中的code等
        // 用户权限列表
        List<String> permissions = new ArrayList<>();
        permissions.add("insert");
        permissions.add("update");
        permissions.add("remove");
        permissions.add("delete");
        List<GrantedAuthority> grantedAuthorities = permissions.stream().map(GrantedAuthorityImpl::new).collect(Collectors.toList());
        // 缓存中的验证码
        password = "1234";
        // 用户账号状态
        flag = true;
        authorities = grantedAuthorities;

        return new JwtUserDetails(username, password, flag, authorities);
    }

}
