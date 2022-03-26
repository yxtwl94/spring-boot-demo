package com.oleyang.springbootdemo.config;
import com.oleyang.springbootdemo.handler.MyAccessDeniedHandler;
import com.oleyang.springbootdemo.handler.UnAuthorizedHandler;
import com.oleyang.springbootdemo.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/*
spring security全局配置文件，很重要，先从这里入手
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
/* 这个配置开启了三个注解
@PreAuthorize：方法执行前进行权限检查
@PostAuthorize：方法执行后进行权限检查
@Secured：类似于 @PreAuthorize
 */
@EnableWebSecurity // 1
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*
        认证过滤器，在UsernamePasswordAuthenticationFilter过滤器之前进行，认证失败会抛出异常（不返回）
        认证流程：
            1. 从请求头拿到user_token值（请求头在前端自动加）
            2. 如果是login不需要请求头，直接放行，放行后会在authenticationManager里面校验用户密码然后添加token到redis（参见userdetail）
            3. 若果是其他请求，首先解析header里的token，解析出来username
            4. 用username匹配redis里面的真实token，防止前端token过期的情况
            5. 成功后放行，以上失败都会抛出500异常
     */
    @Autowired
    JwtAuthFilter jwtAuthFilter;

    /*
        错误处理器，用户在登陆失败后不抛出异常返回一个json方便前端进行alert
            1、直接定义res的格式即可
     */
    @Autowired
    UnAuthorizedHandler unAuthorizedHandler;

    @Autowired
    MyAccessDeniedHandler myAccessDeniedHandler;

    // 注入密码加密器，匹配的时候会自动采用该加密器进行匹配，在userdetail service验证使用
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用csrf
        http.csrf().disable()
                .logout().disable();
        // 自添加定义过滤器，用户token鉴权
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // 添加认证失败处理器
        http.exceptionHandling().authenticationEntryPoint(unAuthorizedHandler);
        // 添加权限异常处理器
        http.exceptionHandling().accessDeniedHandler(myAccessDeniedHandler);
    }

    // 这样就可以从容器里获取到manager
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
