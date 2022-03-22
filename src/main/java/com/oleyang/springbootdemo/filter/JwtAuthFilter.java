package com.oleyang.springbootdemo.filter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oleyang.springbootdemo.dao.User;
import com.oleyang.springbootdemo.mapper.UserMapper;
import com.oleyang.springbootdemo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserMapper userMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String headerToken = request.getHeader("user_token");
        // login和注册 请求放行
        String servletPath = request.getServletPath();
        if (Objects.equals(servletPath, "/login") || Objects.equals(servletPath, "/register")) {
            // 放行，一般是登录不携带token
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token，获取username
        JwtUtil jwtUtil = new JwtUtil();
        String username;
        try {
            Claims claims = jwtUtil.parseJWT(headerToken);
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("token验证失败了");
        }
        // 根据username反查token，如果查到，存入username到security context
        String redisKey = username;
        // 根据username查到的redis有效token，保证客户端cookie的token过期可以匹配失败
        String redisToken = stringRedisTemplate.opsForValue().get(redisKey);
        if (Objects.equals(redisToken, headerToken)){
            // 认证通过，根据username查到 获取user对象
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq("username", username);
            User user = userMapper.selectOne(qw);
            // todo 获取权限信息
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(user,null, null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);
        } else {
            throw new RuntimeException("redis token过期");
        }

    }
}
