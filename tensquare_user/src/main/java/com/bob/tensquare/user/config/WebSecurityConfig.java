package com.bob.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration//声明为配置类
@EnableWebSecurity//开启安全链
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //authorizeRequests 所有security全注解配置实现的开端，表开启权限
        //需要两部分权限：拦截路径、访问该路径需要的权限
        //antMatchers 表示拦截路径，permitAll:任何权限都可以放行
        //anyRequest()任意请求，authenticated：认证后才能访问
        //.and().csrf().disable():固定写法，表示csrf拦截失效
        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
