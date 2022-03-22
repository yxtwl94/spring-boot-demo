package com.oleyang.springbootdemo;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootAmqpTest {
//    @Autowired
//    RabbitTemplate rabbitTemplate;
//
//    @Test
//    public void testSendDirectMsg(){
//        String queueName = "simple.queue";
//        String msg = "hello";
//        // 注入消息，阅后即焚类型direct
//        rabbitTemplate.convertAndSend(queueName, msg);
//    }
//
//    // work queue模式，一个队列对应多个消费者处理
//    @Test
//    public void testSendWorkQueueMsg() throws InterruptedException {
//        String queueName = "simple.queue";
//
//        // work queue模拟，产生50个数据
//        for (int i = 0; i < 50; i++) {
//            String msg = "hello_" + i;
//            rabbitTemplate.convertAndSend(queueName, msg);
//        }
//    }

}
