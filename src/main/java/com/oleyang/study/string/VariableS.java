package com.oleyang.study.string;

import sun.misc.VM;

public class VariableS {
    public static void main(String[] args) {
        Integer a = 1;
        System.out.println(System.identityHashCode(a));
        Integer b = 2;
        System.out.println(add(a, b));
        System.out.println(a);
    }

    public static Integer add(Integer a, Integer b) {
        // 拿到的a内存地址，但是不可修改
        System.out.println(System.identityHashCode(a));
        a = a + 1;  // 这一步是新建了一个a，和外面的a不一样了
        System.out.println(System.identityHashCode(a));

        b++;
        return a+b;
    }
}
