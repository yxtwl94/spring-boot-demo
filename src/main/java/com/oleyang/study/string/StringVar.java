package com.oleyang.study.string;

public class StringVar {
    public static void main(String[] args) {

        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        // 转换ABC到数字
        for (int i = 0; i < str.length(); i++) {
            System.out.println(str.codePointAt(i) -64);
        }

        String base = "http://%s";
        String host = "www.baidu.com";
        String url = String.format(base, host);
        System.out.println(url);
    }
}
