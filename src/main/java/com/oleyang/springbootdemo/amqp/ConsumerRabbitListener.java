package com.oleyang.springbootdemo.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerRabbitListener {

    // 监听特定队列的消息然后拿到消息运行，消息的塞入在SpringBootAmqpTest
//    @RabbitListener(queues = "simple.queue")
//    public void listenQueue(String msg){
//        System.out.println("当队列收到消息后，消费者会获得这个消息:" + msg);
//    }

    // work queue消费者1
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue1(String msg) throws InterruptedException {
        System.out.println("消费者1接收到消息==========>" + msg);
        Thread.sleep(20);
    }

    // work queue消费者1
    @RabbitListener(queues = "simple.queue")
    public void listenWorkQueue2(String msg) throws InterruptedException {
        System.err.println("消费者2接收到消息==========>" + msg);
        Thread.sleep(20);
    }

}
