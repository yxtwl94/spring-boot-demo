package com.oleyang.study.string;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.rabbitmq.client.BlockedCallback;

public class StringDemo {
    public String test;

    public static void main(String[] args) {
        // 类变脸自动初始化为Null
        System.out.println(new StringDemo().test);
        // 局部变量必须初始化为空字符串
        String demo1 = null;
        System.out.println(demo1);
        // lamda表达式中的变量必须是final的，如果不是会自动加
        LambdaTest l = (i, j) -> {
            // 这里面虽然修改了但是不会影响到外面的变量
            i++;
        };
        int i = 1;
        int j = 2;
        l.add(i,j);
        System.out.println(i);
    }

    interface LambdaTest {
        void add(int i, int j);
    }

}
