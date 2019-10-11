package com.eight.lambda.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: eight
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 16:21
 **/
public class StreamTest {


    public static void main(String[] args) {

        List<String> wordsList = new ArrayList<>();
        wordsList.add("Charles");
        wordsList.add("Vincent");
        wordsList.add("William");
        wordsList.add("Joseph");
        wordsList.add("Henry");
        wordsList.add("Bill");
        wordsList.add("Joan");
        wordsList.add("Linda");
        wordsList.add("Joan");
        wordsList.add("Linda");
        int count = 0;

        for (String word : wordsList)
        {
            if(word.length()>6)
                count++;
        }

        System.out.println(count);


        //过滤
        long count2 = wordsList.stream().filter(e->e.length()>6).count();

        long count3 = wordsList.parallelStream().filter(e->e.length()>6).count();

        List<String> list2 =  wordsList.stream().filter(e->e.length()>6).collect(Collectors.toList());

        List<String> list3 = wordsList.stream().filter(e->e.length()>6).collect(Collectors.toList());

        list2.forEach(System.out::println);
        list3.forEach(System.out::println);

        System.out.println(count2);
        System.out.println(count3);

        //去重
        List<String> disList = wordsList.stream().distinct().collect(Collectors.toList());


        wordsList.forEach(System.out::println);
        System.out.println("***********");
        disList.forEach(System.out::println);


        List<Integer> numbers = Arrays.asList(3,2,2,3,7,3,5);

        //map 方法用于映射每个元素到对应的结果，一下代码片段使用map输出了元素对应的平方数
        List<Integer> squaresList = numbers.stream().map(e->e*e).distinct().collect(Collectors.toList());

        System.out.println(squaresList.toString());
    }

}

