package com.oleyang.springbootdemo.service;

import com.oleyang.springbootdemo.dao.ResponseResult;
import com.oleyang.springbootdemo.dao.User;

public interface IUserService {
    // 用户登录
    ResponseResult loginWithSecurity(User user);
    // 用户注册
    ResponseResult register(User user);
    // 获取用户信息
    ResponseResult getUserInfo();
    // 登出
    ResponseResult logout();
    // 编辑用户
    ResponseResult editUser(User user);
    // 获取用户总数
    ResponseResult getUserNum(String username);
    // 获取所有用户信息
    ResponseResult getUserPage(String username, int cur, int size);
    // 删除用户
    ResponseResult deleteUser(String username);
    // 查找用户
    ResponseResult searchUser(String username, int cur, int size);
    // 获取一个用户
    ResponseResult getOneUser(String username);

}
