package com.oleyang.study.string;

public class StringVar {
    public static void main(String[] args) {
        String base = "http://%s";
        String host = "www.baidu.com";
        String url = String.format(base, host);
        System.out.println(url);
    }
}
