package com.oleyang.springbootdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oleyang.springbootdemo.dao.ResponseResult;
import com.oleyang.springbootdemo.dao.User;
import com.oleyang.springbootdemo.mapper.UserMapper;
import com.oleyang.springbootdemo.service.UserService;
import com.oleyang.springbootdemo.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {

	@Autowired
	UserMapper userMapper;
	@Autowired
	UserService userService;
	@Autowired
	StringRedisTemplate stringRedisTemplate;

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
		boolean matches = bCryptPasswordEncoder.matches(rawPass, encodePass);
		assert matches;
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

	@Test
	void testJoin(){
		List<User> joinUser = userMapper.getUserJoinById(1);
		System.out.println(joinUser);
		assert !joinUser.get(0).getInfo().isEmpty();
	}

	@Test
	void testLogin(){
		User user = new User();
		user.setPassword("123");
		user.setUsername("admin");
		ResponseResult responseResult = userService.loginWithSecurity(user);
		System.out.println(responseResult);
		assert responseResult.getCode() == 200;
		stringRedisTemplate.delete("admin");

	}

}
