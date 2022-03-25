package com.oleyang.springbootdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oleyang.springbootdemo.dao.User;
import com.oleyang.springbootdemo.dao.UserDetail;
import com.oleyang.springbootdemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/*
重写spring security获取用户信息的方法
 */
@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", username);
        User user = userMapper.selectOne(qw);
        if (Objects.isNull(user)){
            // 抛出异常会传到exception filter
            throw new RuntimeException("没有找到用户");
        }
        // 查到了就构造userDetail对象
        return new UserDetail(user);
    }
}
