package com.oleyang.springbootdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oleyang.springbootdemo.config.SecurityConfig;
import com.oleyang.springbootdemo.dao.User;
import com.oleyang.springbootdemo.mapper.UserMapper;
import com.oleyang.springbootdemo.service.UserService;
import com.oleyang.springbootdemo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@SpringBootTest
class SpringBootDemoApplicationTests {
	@Autowired
	UserService userService;

	@Autowired
	SecurityConfig securityConfig;

	@Autowired
	UserMapper userMapper;

	@Test
	void testUserMapper(){
		QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
		userQueryWrapper.eq("id", 1);
		User user = userMapper.selectOne(userQueryWrapper);
		System.out.println(user);
	}

	@Test
	void testPassEncoder(){
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String rawPass = "123";
		String encodePass = bCryptPasswordEncoder.encode(rawPass);
		System.out.println(encodePass);
	}

	@Test
	void testJWT(){
		// 实例化
		JwtUtil jwtUtil = new JwtUtil();

		String jwt = jwtUtil.createJWT("oleyang", 10000); // 10秒过期
		System.out.println(jwt);

		// 解析jwt
		Claims claims = jwtUtil.parseJWT(jwt);
		// 获取用户名信息
		Object username = claims.get("username");
		System.out.println("用户名:"+username); // 解析token
	}

}
