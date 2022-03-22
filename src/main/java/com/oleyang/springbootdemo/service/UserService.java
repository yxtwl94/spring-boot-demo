package com.oleyang.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oleyang.springbootdemo.dao.ResponseResult;
import com.oleyang.springbootdemo.dao.User;
import com.oleyang.springbootdemo.mapper.UserMapper;
import com.oleyang.springbootdemo.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    UserMapper userMapper;

    // 检查用户是否存在，密码是否准确
    public ResponseResult loginWithSecurity(User user){
        // 1 security config注入manager（里面有Password encoder）
        // 2 重写userservice方法，获取用户密码
        // 3 封装userdetail体，如果匹配成功则返回
        UsernamePasswordAuthenticationToken usernamePassAuth =
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePassAuth);
        if (Objects.isNull(authentication)){
            // 认证没通过
            return new ResponseResult(HttpStatus.UNAUTHORIZED.value(),"登陆失败", new Date(), "");
        }
        // 认证通过 jwt生成token
        JwtUtil jwtUtil = new JwtUtil();
        // 默认过期时间1天
        long expireTime = 1000 * 60 * 60 * 24;
        String jwt_token = jwtUtil.createJWT(user.getUsername(), expireTime);
        // 存入redis
        stringRedisTemplate.opsForValue().set(user.getUsername(), jwt_token, expireTime, TimeUnit.MILLISECONDS);
        return new ResponseResult(HttpStatus.OK.value(), "登陆成功", new Date(), jwt_token);
    }

    public ResponseResult getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        ResponseResult res = new ResponseResult(HttpStatus.OK.value(), "获取用户信息成功", new Date(), user);
        return res;
    }

    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        // 删除redis信息
        stringRedisTemplate.delete(username);
        return new ResponseResult(HttpStatus.OK.value(), "注销成功", new Date(), null);
    }

    // 自己实现的认证，不依赖security
//    public ResponseResult myLogin(User user){
//        // 1 检查用户密码正确
//        QueryWrapper<User> qw = new QueryWrapper<User>();
//        qw.eq("username", user.getUsername());
//        User sql_user = userMapper.selectOne(qw);
//        if (Objects.isNull(sql_user)){
//            return new ResponseResult(401,"用户不存在", new Date(), "");
//        } else {
//            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//            if (bCryptPasswordEncoder.matches(user.getPassword(), sql_user.getPassword())){
//                // 密码匹配成功
//                // 认证通过 jwt生成token
//                JwtUtil jwtUtil = new JwtUtil();
//                // 默认过期时间1天
//                long expireTime = 1000 * 60 * 60 * 24;
//                String jwt_token = jwtUtil.createJWT(user.getUsername(), expireTime);
//                // 存入redis, token是key
//                stringRedisTemplate.opsForValue().set( jwt_token,user.getUsername(), expireTime, TimeUnit.MILLISECONDS);
//                return new ResponseResult(200,"登录成功", new Date(), jwt_token);
//            } else {
//                return new ResponseResult(403,"密码错误", new Date(), "");
//            }
//        }
//    }

//    public ResponseResult verifyToken(String token){
//        String username = stringRedisTemplate.opsForValue().get(token);
//        if (username != null){
//            return new ResponseResult(200,"用户存在", new Date(), username);
//        } else{
//            return new ResponseResult(400,"token验证失败", new Date(), "");
//        }
//    }

}