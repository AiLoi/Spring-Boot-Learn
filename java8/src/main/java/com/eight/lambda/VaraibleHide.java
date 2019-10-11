package com.eight.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program: eight
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 15:09
 **/
public class VaraibleHide {

    @FunctionalInterface
    interface IInner {
        void printInt(int x);
    }


    public static void main(String[] args) {
        int x = 20;

        IInner iInner = new IInner() {
            @Override
            public void printInt(int x) {
                System.out.println(x);
            }
        };
        iInner.printInt(30);

        iInner = (s)-> System.out.println(x);

        iInner.printInt(30);


        /****************************************/

        List<Integer> list = new ArrayList<>();
        for (int i = 0 ;i<10;i++)
        {
            list.add(i);
        }

        list.forEach(System.out::print);

        list.forEach(VaraibleHide::myPrint);


        String []strings = new String[10];

        Arrays.sort(strings,String::compareToIgnoreCase);




    }


    static void myPrint(int i){
        System.out.print(i+",");
    }

}

