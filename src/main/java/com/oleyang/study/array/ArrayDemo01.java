package com.oleyang.study.array;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayDemo01 {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String str = iterator.next();
            if (str.equals("c")) {
                iterator.remove();
            }
        }
        System.out.println(list);
    }
}
