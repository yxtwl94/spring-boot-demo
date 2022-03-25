package com.oleyang.springbootdemo.controller;

import com.oleyang.springbootdemo.dao.ResponseResult;
import com.oleyang.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@PreAuthorize("hasRole('admin')")
public class PermissionController {
    @Autowired
    UserService userService;

    @GetMapping("/testAuth")
    public String testAuth(){
        return "success admin";
    }

    @GetMapping("/getUserPage")
    public ResponseResult getUserPage(String username, int cur, int size){
        return userService.getUserPage(username, cur, size);
    }

    @GetMapping("/getOneUser")
    public ResponseResult getOneUser(String username){
        return userService.getOneUser(username);
    }

    @DeleteMapping("/deleteUser")
    public ResponseResult deleteUser(String username){
        ResponseResult res = userService.deleteUser(username);
        return res;
    }
}
