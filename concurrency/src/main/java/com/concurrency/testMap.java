package com.concurrency;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: concurrency
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-26 17:58
 **/
public class testMap {


    public static void main(String[] args) {

        Map<Integer,Object> map = new HashMap<>();

        map.put(1,"aaa");
        map.put(2,"bbb");
        map.put(3,"ccc");

        for(Integer temp : map.keySet())
        {
            System.out.println(map.get(temp));
        }

    }


}

