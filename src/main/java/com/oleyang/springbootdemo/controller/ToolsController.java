package com.oleyang.springbootdemo.controller;

import com.oleyang.springbootdemo.dao.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@RestController
public class ToolsController {

    @PostMapping("/tools/uploadFile")
    public ResponseResult uploadFile(MultipartFile file) throws IOException {
        // 读取csv文件
        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line = null;
        // 读取标题头
        HashMap<String, Object> map = new HashMap<>();
        String headers = reader.readLine();
        String[] header = headers.split(",");
        for (String s : header) {
            map.put(s, new ArrayList<>());
        }
        // 读取实际内容
        while ((line = reader.readLine()) != null) {
            String[] str = line.split(",");
            for (int i = 0; i < str.length; i++) {
                ((ArrayList) map.get(header[i])).add(str[i]);
            }
        }
        // 返回map到前端
        return new ResponseResult(200, "上传成功", new Date(), map);
    }
}
