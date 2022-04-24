package com.oleyang.study.thread;
import lombok.Data;

@Data
public class MyData {
    private int num = 100;
    // 单例模式
    private static MyData sing = new MyData();
    private MyData() {}

    public static MyData getInstance() {
        return sing;
    }

    // 修改数据
    public void changeNum() {
        num--;
    }

}
