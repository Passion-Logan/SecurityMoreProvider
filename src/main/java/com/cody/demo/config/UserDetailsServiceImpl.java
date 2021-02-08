package com.cody.demo.config;

import com.cody.demo.dao.PcUserDao;
import com.cody.demo.entity.PcUserEntity;
import com.cody.demo.security.JwtUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PcUserDao pcUserDao;

    /**
     * 根据账号获取用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PcUserEntity byPhone = pcUserDao.findByPhone(username);

        if (null == byPhone) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return new JwtUserDetails(byPhone.getPhone(), byPhone.getPassword());
    }
}
