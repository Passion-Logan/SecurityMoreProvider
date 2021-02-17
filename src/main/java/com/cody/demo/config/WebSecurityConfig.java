package com.cody.demo.config;

import com.alibaba.fastjson.JSONObject;
import com.cody.demo.security.AdminAuthenticationProvider;
import com.cody.demo.security.CusAuthenticationSuccessHandler;
import com.cody.demo.security.CustomAuthenticationProcessingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CusAuthenticationSuccessHandler successHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置认证方式等
        auth.authenticationProvider(adminAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http相关的配置，包括登入登出、异常处理、会话管理等
        http.cors().and().csrf().disable()
                .authorizeRequests()
//                .antMatchers("/home/login").permitAll()
                .anyRequest().authenticated();

        //各类错误异常处理 以下针对于访问资源路径 认证异常捕获 和 无权限处理
        http.exceptionHandling().authenticationEntryPoint((req, resp, exception) -> {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            //封装异常描述信息
            String json = JSONObject.toJSONString("登录失效,请重新登录");
            out.write(json);
            out.flush();
            out.close();
        }).accessDeniedHandler((resq, resp, exception) -> {
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            String json = JSONObject.toJSONString("无权限：");
            out.write(json);
            out.flush();
            out.close();
        });

        http.addFilterAfter(externalAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public CustomAuthenticationProcessingFilter externalAuthenticationProcessingFilter() {
        CustomAuthenticationProcessingFilter filter = new CustomAuthenticationProcessingFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setAuthenticationSuccessHandler(successHandler);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public AdminAuthenticationProvider adminAuthenticationProvider() {
        AdminAuthenticationProvider adminAuthenticationProvider = new AdminAuthenticationProvider();
        return adminAuthenticationProvider;
    }

    /**
     * 新版本的security规定必须设置一个默认的加密方式
     * 用于登录验证 注册等
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
