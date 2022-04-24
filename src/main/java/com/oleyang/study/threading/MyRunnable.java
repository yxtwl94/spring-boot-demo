package com.oleyang.study.threading;

import java.util.concurrent.ArrayBlockingQueue;

public class MyRunnable implements Runnable {
    private Boolean exit = false;
    private ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);

    @Override
    public void run() {
        while (!exit) {
            // 生成随机数
            int num = (int) (Math.random() * 100);
            // 将随机数放入队列
            try {
                // 等待1.5s模拟
                Thread.sleep(1500);
                queue.put(num);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void exit() {
        exit = true;
    }

    public Integer getData() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
