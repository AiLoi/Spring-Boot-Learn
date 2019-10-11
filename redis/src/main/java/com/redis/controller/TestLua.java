package com.redis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: redis
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-27 16:01
 **/

@RestController
@RequestMapping("/lua")
public class TestLua {


    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/hello")
    public Map<String,Object> testLua(){

        DefaultRedisScript<String> rs = new DefaultRedisScript<>();

        rs.setScriptText("return 'Hello Redis'");

        rs.setResultType(String.class);
        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();


        /*
        execute 有两个参数接口，一种存在序列化参数，一种不存在序列化参数
        1.不存在序列化参数:Spring将采用RedisTemplate提供的valueSerializer序列化器对传递的键和参数进行序列化

         */


        /*
        template:EVAL script numkeys key [key ...] arg [arg ...]　
        eg:eval "return {KEYS[1],KEYS[2],ARGV[1],ARGV[2]}" 2 key1 key2 first second
        1.script : 脚本字符串
        2.numbers : 参数个数
        3.key[key...]: 参数
        4.arg[arg...]
        I.“ 2 ” :脚本参数个数
        II.key1  :参数1
        III.key2  :参数2
        IV.first : 额外参数1
        v: 额外参数2

        在Lua脚本中，可以使用两个不同函数来执行Redis命令，他们分别是
        redis.call()
        redis.pcall()
        :区别在于使用不同得方式处理执行命令所产生的错误

        --eval 命令语义，脚本里使用的所有键都应该由KEYS数组来传递
        eg: eval "return redis.call('set',KEYS[1],'bar')" 1 foo




        Redis 实现了EVALSHA命令，它的作用和eval一样，都用于对脚本求值，但是它接受的第一个参数不是脚本，而是脚本的SHA1校验和（sum）。
        EVALSHA 命令的表现如下
        1.如果服务器还记得给定SHA1校验和指定的脚本，那么执行这个脚本

        2.如果服务器不记得给定的SHA1校验和所指定的脚本，那么它返回一个特殊的错误。提醒用户使用EVAL代替EVALSHA

        Redis 保证所有被运行过的脚本都被永久保存在脚本缓存当中，这意味着，当EVAL命令在一个Redis实例上成功执行某个脚本后，随后针对这个脚本所有EVALSHA命令都会成功执行。
        Redis提供了以下几个SCRIPT命令，用于对脚本系统进行控制

        SCRIPT FLUSH:清除所有脚本缓存
        SCRIPT EXISTS:根据给定的脚本校验和检查指定的脚本是否存在于脚本系统
        SCRIPT LOAD:将一个脚本装入脚本缓存，但并不立即运行它
        SCRIPT KILL:杀死当前正在运行的脚本

         */


        String str = (String) redisTemplate.execute(rs,stringRedisSerializer,stringRedisSerializer,null);

        Map<String,Object> map = new HashMap<>();
        map.put("str",str);
        return map;

    }



    @RequestMapping("/lua2")
    public Map<String,Object> testLua2 (String key1,String key2,String value1,String value2){

        String lua = "redis.call('set',KEYS[1],ARGV[1])\n"
                +"redis.call('set',KEYS[2],ARGV[2])\n"
                +"local str1 = redis.call('get',KEYS[1])\n"
                +"local str2 = redis.call('get',KEYS[2])\n"
                +"if str1 == str2 then  \n"
                +"return 1\n"
                +"end \n"
                +"return 0\n";

        System.out.println(lua);
        DefaultRedisScript<Long> rs = new DefaultRedisScript<>();
        rs.setScriptText(lua);
        rs.setResultType(Long.class);

        RedisSerializer<String> stringRedisSerializer = redisTemplate.getStringSerializer();

        List<String> keyList = new ArrayList<>();

        keyList.add(key1);
        keyList.add(key2);
        Long result = (Long) redisTemplate.execute(rs,stringRedisSerializer,stringRedisSerializer,keyList,value1,value2);

        Map<String,Object> map = new HashMap<>();
        map.put("result",result);
        return map;

    }

}

