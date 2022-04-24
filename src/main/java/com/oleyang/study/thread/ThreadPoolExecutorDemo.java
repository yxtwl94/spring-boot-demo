package com.oleyang.study.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                20, 100, 1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 100; i++) {
            Runnable  worker = new MyRunnable();
            // 将任务提交给线程池
            threadPoolExecutor.execute(worker);
        }

        // 关闭线程池
        threadPoolExecutor.shutdown();
    }
}
