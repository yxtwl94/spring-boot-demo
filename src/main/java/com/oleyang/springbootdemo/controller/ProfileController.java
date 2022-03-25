package com.oleyang.springbootdemo.controller;

import com.oleyang.springbootdemo.dao.ResponseResult;
import com.oleyang.springbootdemo.dao.User;
import com.oleyang.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    @Autowired
    UserService userService;

    @PutMapping("/editUser")
    public ResponseResult editProfile(@RequestBody User user){
        ResponseResult res = userService.editUser(user);
        return res;
    }

    @GetMapping("/testAuth")
    @PreAuthorize("hasRole('admin')")  // 需要用户有admin权限才可以
    public String testAuth(){
        return "success admin";
    }
}
