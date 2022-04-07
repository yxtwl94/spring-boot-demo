package com.oleyang.springbootdemo.dao;

import org.springframework.boot.configurationprocessor.json.JSONArray;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.ArrayList;

public class WebSocketEncoder implements Encoder.Text<ArrayList>{
    @Override
    public String encode(ArrayList arrayList) {
        // 返回json格式的数据
        JSONArray jsonArray = new JSONArray();
        for (Object o : arrayList) {
            jsonArray.put(o);
        }
        return jsonArray.toString();
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
