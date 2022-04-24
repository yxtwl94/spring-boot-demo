package com.oleyang.study.array;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ArrayStudy {
    // 一些数组的基本操作
    public static void main(String[] args) throws IOException {
        // 定义一个数组
        int[] array = new int[5];
        // 初始化数组
        array[0] = 5;
        array[1] = 4;
        array[2] = 3;
        array[3] = 2;
        array[4] = 1;
        // 打印数组
        System.out.println(Arrays.toString(array));
        // 打印数组的长度
        System.out.println(array.length);
        // 数组排序
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));

        // 定义一个对象数组
        Person p1 = new Person("张1", 20);
        Person p2 = new Person("张2", 30);
        Person p3 = new Person("张3", 10);
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        // 打印对象数组
        // personList.sort(Comparator.comparingInt(Person::getAge));
        Collections.sort(personList);
        System.out.println(personList);

        // 数组保存到文件
        File file = new File("test.csv");
        OutputStreamWriter ow = new OutputStreamWriter(new FileOutputStream(file), "gbk");
        BufferedWriter bw = new BufferedWriter(ow);
        bw.write("姓名,年龄\n");
        for (Person person : personList) {
            bw.write(person.getName() + "," + person.getAge() + "\n");
        }
        bw.flush();
        bw.close();
        // 数组从文件读取
        InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");
        BufferedReader br = new BufferedReader(isr);
        String line = br.readLine();
        while (line != null) {
            System.out.println(line);
            line = br.readLine();
        }
        br.close();
        // 删除文件
        file.delete();

    }
}
