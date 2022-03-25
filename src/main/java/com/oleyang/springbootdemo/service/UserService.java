package com.oleyang.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.List;
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

    public ResponseResult register(User user) {
        // 传入的是明文密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePass = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodePass);
        // 默认权限是空类型，一般权限不单独设置
        try {
            userMapper.insert(user);
        } catch (Exception e){
            return new ResponseResult(HttpStatus.CONFLICT.value(), "数据库插入操作异常", new Date(), null);
        }
        return new ResponseResult(HttpStatus.OK.value(), "注册成功", new Date(), user);
    }

    public ResponseResult editUser(User user) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User realUser = (User) authentication.getPrincipal();
        // 根据用户名唯一性进行修改
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", user.getUsername());
        try {
            // qw匹配已有用户，替换为user
            userMapper.update(user, qw);
        } catch (Exception e){
            return new ResponseResult(HttpStatus.CONFLICT.value(), "修改失败", new Date(), null);
        }
        return new ResponseResult(HttpStatus.OK.value(), "修改成功", new Date(), user);
    }

    public ResponseResult getUserNum(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("username", username);
        Long num = userMapper.selectCount(qw);
        return new ResponseResult(HttpStatus.OK.value(), "成功", new Date(), num);
    }

    public ResponseResult getUserPage(String username, int cur, int size) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("username", username);

        Page<User> page = new Page<>(cur, size);
        Page<User> userPage = userMapper.selectPage(page, qw);
        List<User> users = userPage.getRecords();
        return new ResponseResult(HttpStatus.OK.value(), "成功", new Date(), users);
    }

    public ResponseResult deleteUser(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        try {
            userMapper.delete(qw);
            return new ResponseResult(HttpStatus.OK.value(), "删除成功", new Date(), null);
        } catch (Exception e){
            return new ResponseResult(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), new Date(), null);
        }
    }

    public ResponseResult searchUser(String username, int cur,int size) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.like("username", username);
        try {
            Page<User> page = new Page<>(cur, size);
            Page<User> users = userMapper.selectPage(page, qw);
            return new ResponseResult(HttpStatus.OK.value(), "成功", new Date(), users.getRecords());
        } catch (Exception e){
            return new ResponseResult(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), new Date(), null);
        }
    }
    // 获取一个用户
    public ResponseResult getOneUser(String username) {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        try {
            User user = userMapper.selectOne(qw);
            return new ResponseResult(HttpStatus.OK.value(), "成功", new Date(), user);
        } catch (Exception e){
            return new ResponseResult(HttpStatus.NOT_ACCEPTABLE.value(), HttpStatus.NOT_ACCEPTABLE.getReasonPhrase(), new Date(), null);
        }
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
