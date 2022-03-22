package com.oleyang.springbootdemo.exceptionHandler;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// 登录失败处理器（用户密码不匹配）
@Component
public class UnAuthorizedHandler implements AuthenticationEntryPoint{
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 设置response返回格式
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        JSONObject json = new JSONObject();
        try {
            json.put("code", HttpStatus.UNAUTHORIZED.value());
            json.put("message", "用户或密码错误");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        writer.write(json.toString());
    }
}
