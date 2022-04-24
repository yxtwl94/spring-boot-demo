package com.oleyang.study.thread;

import java.util.concurrent.CountDownLatch;

public class CountLatchDownDemo {

    public static void main(String[] args) throws InterruptedException {
        // 定义计数器
        CountDownLatch countDownLatch = new CountDownLatch(5);
        // 创建5个线程
        for (int i = 0; i < 5; i++) {
            MyRunnable worker = new MyRunnable();
            worker.setCountDownLatch(countDownLatch);
            new Thread(worker).start();
        }
        // 等待所有线程执行完毕
        countDownLatch.await();
        System.out.println("所有线程执行完毕");
    }
}
