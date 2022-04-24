package com.oleyang.study.thread;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class MyRunnable implements Runnable {

    private CountDownLatch countDownLatch;

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
        try {
            processCmd();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
        // 计数器结束 -1
        countDownLatch.countDown();
    }

    public void processCmd() throws InterruptedException {
        Thread.sleep(1000);
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