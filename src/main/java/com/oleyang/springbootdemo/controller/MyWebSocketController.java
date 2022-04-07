package com.oleyang.springbootdemo.controller;

import com.oleyang.springbootdemo.dao.WebSocketEncoder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

@ServerEndpoint(value = "/ws", encoders = {WebSocketEncoder.class})
@Controller
@EnableScheduling
public class MyWebSocketController{
    // 在线人数,缓存session用作消息推送
    public static HashSet<Session> sessionSet = new HashSet<>();
    // 缓存消息
    public static ArrayList<String> webSocketMsgList = new ArrayList<>();

    @OnOpen
    public void onOpen(Session session){
        sessionSet.add(session);
        System.out.println("session: " + session.getId() + " 当前人数：" + sessionSet.size());
    }

    // 收到前端信息, 返回所有消息列表
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, EncodeException {
        System.out.println("收到消息：" + message);
        webSocketMsgList.add(message);
        session.getBasicRemote().sendObject(webSocketMsgList);
    }

    // 错误调用
    @OnError
    public void onError(Session session, Throwable error){
        System.out.println("error " + error.getMessage());
    }

    // 关闭连接
    @OnClose
    public void onClose(Session session){
        sessionSet.remove(session);
        System.out.println("session: " + session.getId() + "关闭 " + "当前人数：" + sessionSet.size());
    }

    // 1秒推送一次
    @Scheduled(cron = "*/1 * * * * ?")
    public void sendMsg() throws IOException, EncodeException {
        // 发送消息
        for (Session session : sessionSet) {
            session.getBasicRemote().sendObject(webSocketMsgList);
        }
    }

}
