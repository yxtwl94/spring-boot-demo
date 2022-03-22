package com.oleyang.springbootdemo;

import com.oleyang.springbootdemo.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class SpringDataRedisTest {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    void testRedis(){
        // 重复操作会覆盖原有值
        stringRedisTemplate.opsForValue().set("key1", "value1", 1,TimeUnit.DAYS);
        String value = stringRedisTemplate.opsForValue().get("key1");
        System.out.println(value);
    }

    @Test
    void testToken(){
        JwtUtil jwtUtil = new JwtUtil();
        // 默认过期时间1天
        long expireTime = 1000 * 60 * 60 * 24;
        String jwt_token = jwtUtil.createJWT("oleyang", expireTime);
        System.out.println(jwt_token);

        String jwt_token2 = jwtUtil.createJWT("oleyang", expireTime);
        System.out.println(jwt_token2);
    }
}
