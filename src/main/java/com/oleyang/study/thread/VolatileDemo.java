package com.oleyang.study.thread;

public class VolatileDemo extends Thread {
    // 设置volatile会强制从主程序中读取flag更新的值
    volatile boolean flag = false;
    int i = 0;

    @Override
    public void run() {
        while (!flag) {
            // 停止一秒
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 这里读取的一直是false，所以不会退出。因为线程是在本身的内存中读取flag的
            System.out.println(flag);
            i++;
        }
    }

    public static void main(String[] args) {
        VolatileDemo demo = new VolatileDemo();
        demo.start();
        // 暂停一秒钟
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 这里设置后，如果没有volatile线程不会退出
        demo.flag = true;
    }
}
