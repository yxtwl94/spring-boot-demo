package com.oleyang.study.thread;

import java.util.concurrent.*;

public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        // corePoolSize:执行线程池的最小线程数
        // maximumPoolSize:执行线程池的最大线程数

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                20, 30, 100,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(10),
                new ThreadPoolExecutor.AbortPolicy());

        // 创建100个任务，其中20个执行，10个放到队列中等待,剩下70个
        for (int i = 0; i < 100; i++) {
            Runnable  worker = new MyRunnable();

            // 1 首先最多允许20个核心线程执行
            int corePoolSize = threadPoolExecutor.getCorePoolSize();

            // 2 当线程池中的线程数达到corePoolSize值，会把任务放到队列中，等待线程池中的线程执行完任务后，再从队列中取出任务执行
            int size = threadPoolExecutor.getQueue().size();
            System.out.println("queue size is: " + size);

            // 如果队列和核心线程都满了，那么新的任务会被放非核心线程中执行并且带有过期时间
            int poolSize = threadPoolExecutor.getPoolSize();
            System.out.println("core/pool size is: " + corePoolSize + '/' + poolSize);

            try {
                // 将任务提交给线程池
                threadPoolExecutor.execute(worker);
            } catch (Exception e) {
                // 如果maxPoolSize满了，会执行拒绝策略，这个线程倒霉
                System.out.println("拒绝了当前任务" + worker.hashCode());
            }

        }

        for (int i = 0; i < 100; i++) {
            Runnable worker = new MyRunnable();
            threadPoolExecutor.execute(worker);
        }
        // 关闭线程池
        threadPoolExecutor.shutdown();
    }
}
