package com.oleyang.springbootdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.oleyang.springbootdemo.dao.User;
import com.oleyang.springbootdemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringMyBatisTest {
    @Autowired
    UserMapper userMapper;

    @Test
    void testMapper(){
        User oneUser = userMapper.findOneUser(1);
        System.out.println(oneUser);
    }

    @Test
    void testPage(){
        Page<User> page = new Page<>(1, 10);
        Page<User> userPage = userMapper.findPageXml(page);
        System.out.println(userPage.getSize());

        System.out.println(page.getRecords());
    }

    @Test
    void testInsertUpdate(){
        User user = new User();
        user.setUsername("testTime2");
        user.setPassword("123456");
        userMapper.insert(user);
    }

    @Test
    void testUpdateTime(){
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.eq("username", "testTime2");

        User user = new User();
        user.setUsername("testTime");
        userMapper.update(user, qw);
    }
}
