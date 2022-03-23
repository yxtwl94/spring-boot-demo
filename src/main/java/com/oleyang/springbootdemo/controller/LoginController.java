package com.oleyang.springbootdemo.controller;

import com.oleyang.springbootdemo.dao.ResponseResult;
import com.oleyang.springbootdemo.dao.User;
import com.oleyang.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping  //提供统一入口
public class LoginController {

    @Autowired
    UserService userService;

    /*
    login请求，通过用户名密码生成一个token返回
     */
    @ResponseBody //返回请求体
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        ResponseResult res = userService.loginWithSecurity(user);
        return res;
    }

    @GetMapping("/hello")
    @ResponseBody
    public String hello(){
        return "ok";
    }

    @GetMapping("/getUserInfo")
    @ResponseBody
    public ResponseResult getUserInfo(){
        // 首先会进行jwt auth认证然后存入token（token是请求头自动带的）（authorization对象）
        // 能到这一步一定是token认证成功，不然就拦截了
        ResponseResult res = userService.getUserInfo();
        return res;
    }

    @GetMapping("/logout")
    @ResponseBody
    public ResponseResult logout(){
        // 首先会进行jwt auth认证然后存入token（token是请求头自动带的）（authorization对象）
        // 能到这一步一定是token认证成功，不然就拦截了
        ResponseResult res = userService.logout();
        return res;
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseResult register(@RequestBody User user){
        ResponseResult res = userService.register(user);
        return res;
    }

    @GetMapping("/userNum")
    @ResponseBody
    public ResponseResult userNum(){
        ResponseResult res = userService.getUserNum();
        return res;
    }
}
