package com.oleyang.study.thread;

// 共享变量示例
public class ThreadSharedDataDemo implements Runnable {
    // 共享变量
    MyData myData = MyData.getInstance();

    @Override
    public void run() {
        // 获取共享变量
        while (myData.getNum() >0){
            myData.changeNum();
            System.out.println(Thread.currentThread().getName() + ": " + myData.getNum());
        }
    }

    public static void main(String[] args) {
        ThreadSharedDataDemo demo = new ThreadSharedDataDemo();
        Thread thread1 = new Thread(demo, "thread1");
        Thread thread2 = new Thread(demo, "thread2");
        thread1.start();
        thread2.start();
    }
}
