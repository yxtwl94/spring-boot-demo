package com.oleyang.study.thread;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class MyRunnable implements Runnable {

    private CountDownLatch countDownLatch;

    @Override
    public void run() {
        try {
            processCmd();
            System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 计数器结束 -1
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }

    public void processCmd() throws InterruptedException {
        // 随机等待1-5s
        Thread.sleep((long) (Math.random() * 5000));
    }

    // toString
    @Override
    public String toString() {
        return "MyRunnable [countDownLatch=" + countDownLatch + "]";
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
}