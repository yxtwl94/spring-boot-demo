package com.oleyang.study.threading;

public class Main {
    public static void main(String[] args) {
        MyRunnable myRunnable1 = new MyRunnable();
        MyRunnable myRunnable2 = new MyRunnable();
        MyRunnable myRunnable3 = new MyRunnable();
        MyRunnable myRunnable4 = new MyRunnable();
        // 创建线程对象
        Thread t1 = new Thread(myRunnable1);
        Thread t2 = new Thread(myRunnable2);
        Thread t3 = new Thread(myRunnable3);
        Thread t4 = new Thread(myRunnable4);
        // 启动线程
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // 主线程读取4个数据
        while (true){
            System.out.println("主线程读取数据：" + myRunnable1.getData() + " " + myRunnable2.getData() + " " + myRunnable3.getData() + " " + myRunnable4.getData());
        }
    }
}
