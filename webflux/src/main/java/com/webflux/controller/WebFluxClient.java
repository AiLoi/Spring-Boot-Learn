package com.webflux.controller;

import com.webflux.pojo.User;
import com.webflux.pojo.enums.SexEnum;
import com.webflux.pojo.vo.UserPojo;
import com.webflux.pojo.vo.UserVo;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: webflux
 * @description: webflux客户端
 * @author: Ailuoli
 * @create: 2019-06-22 11:33
 **/
public class WebFluxClient {


    public static void main(String[] args) {

        WebClient client = WebClient.create("http://localhost:8080");

        User newUser = new User();

        newUser.setId(2L);
        newUser.setNote("note_new");
        newUser.setUserName("user_name_new");
        newUser.setSexEnum(SexEnum.MALE);

        //插入
//        insertUser(client, newUser);

        //获取
//        getUser(client,2L);

        User upUser = new User();
        upUser.setId(1L);
        upUser.setNote("note_update1");
        upUser.setUserName("user_name_update1");
        upUser.setSexEnum(SexEnum.FEMALE);

        //更新
//        updateUser(client,upUser);

        //模糊查询
//        findUsers(client,"user","note");

        //删除
//        deleteUser(client,2L);

//        getUserPojo(client,1L);

        updateUserName(client,1L,"Ailuoli");

    }


    private static void insertUser(WebClient client, User newUser) {
        Mono<UserVo> userVoMono = client
                .post()                                             //请求类型
                .uri("/user/insert_user")                        //请求路径
                .contentType(MediaType.APPLICATION_STREAM_JSON)     //请求数据类型
                .body(Mono.just(newUser), User.class)               //请求内容
                .accept(MediaType.APPLICATION_STREAM_JSON)          //请求结果类型
                .retrieve()                                         //设置请求结果检索规则
                .bodyToMono(UserVo.class);                          //将结果体转换为Mono封装的数据流

        UserVo userVo = userVoMono.block();                         //获取服务器发布的数据流，此时才会发送请求
        assert userVo != null;
        System.out.println("【用户名称】" + userVo.getUserName());

    }


    private static void getUser(WebClient client, Long id) {
        Mono<UserVo> userVoMono = client
                .get()
                .uri("/user/get_user?id={id}",id)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(UserVo.class);

        UserVo userVo = userVoMono.block();

        assert userVo != null;
        System.out.println("【用户名称】" + userVo.getUserName());

    }


    private static void updateUser(WebClient client, User upUser) {
        Mono<UserVo> userVoMono = client
                .put()
                .uri("/user/update_user")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(Mono.just(upUser), User.class)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(UserVo.class);

        UserVo userVo = userVoMono.block();
        assert userVo != null;
        System.out.println("【用户名称】" + userVo.getUserName());
    }


    private static void findUsers(WebClient client, String userName, String note) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", userName);
        paramMap.put("note", note);
        Flux<UserVo> userVoFlux = client
                .get()
                .uri("/user/find_users?userName={userName}&note={note}", paramMap)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(UserVo.class);

        List<UserVo> list = new ArrayList<>();

        //循环处理流
        for (UserVo item : userVoFlux.toIterable()) {
            list.add(item);
            System.out.println("【用户名称】" + item.getUserName());
        }

        System.out.println(list.toString());
    }

    private static void deleteUser(WebClient client, Long id) {
        Mono<Void> result = client
                .delete()
                .uri("/user/delete_user?id={id}", id)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToMono(Void.class);

        Void voidResult = result.block();
        System.out.println(voidResult);
    }


    //状态判断
    public static void getUser2(WebClient client,Long id){

        Mono<UserVo> userVoMono =
                client.get()
                .uri("/user/get_user?id={id}",id)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                        //发生4开头或者5开头的状态码，4开头是客户端错误，5开头是服务器错误
                        //如果返回状态码是4或5开头则执行让返回流为空
                .retrieve().onStatus(status->status.is4xxClientError()||status.is5xxServerError(),clientResponse -> Mono.empty())
                .bodyToMono(UserVo.class);

        UserVo userVo = userVoMono.block();

        if (userVo!=null){
            System.out.println("【用户名称】"+userVo.getUserName());
        }else {
            System.out.println("服务器没有返回编号为："+id+"的用户");
        }
    }


    //自定义客户端pojo类型
    public static void getUserPojo(WebClient client,Long id){

        Mono<UserPojo> userPojoMono =
                client.get()
                .uri("/user/get_user?id={id}",id)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                        //启用交换
                .exchange()
                        //出现错误返回空
                .doOnError(e->Mono.empty())
                        //获取服务端发送过来的UserVo对象
                .flatMap(clientResponse -> clientResponse.bodyToMono(UserVo.class))
                        //自定义转换为客户端的UserPojo
                .map(WebFluxClient::translate);

        UserPojo userPojo = userPojoMono.block();

        if(userPojo != null){
            System.out.println("获取用户名为"+userPojo.getUserName());
        }else {
            System.out.println("获取的用户编号为"+id+"失败");
        }

    }


    //添加请求头
    public static void updateUserName(WebClient client,Long id,String userName){
        Mono<UserVo> mono = client
                .put()
                .uri("/user/update_username",userName)
                .header("id",id+"")
                .header("userName",userName)
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .onStatus(status -> status.is4xxClientError()||status.is5xxServerError(),clientResponse -> Mono.empty())
                .bodyToMono(UserVo.class);

        UserVo userVo = mono.block();
        if(userVo!=null){
            System.out.println("获取的用户名为："+userVo.getUserName());
        }else {
            System.out.println("获取的用户编号："+id+"失败");
        }
    }




    private static UserPojo translate(UserVo userVo){
        if(userVo==null){
            return null;
        }
        UserPojo userPojo = new UserPojo();

        userPojo.setId(userVo.getId());
        userPojo.setUserName(userVo.getUserName());
        userPojo.setSex(userVo.getSexCode());
        userPojo.setNote(userVo.getNote());

        return userPojo;
    }
}

