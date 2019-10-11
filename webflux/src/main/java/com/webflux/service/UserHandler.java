package com.webflux.service;

import com.webflux.mapper.UserRepository;
import com.webflux.pojo.User;
import com.webflux.pojo.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-22 19:20
 **/
@Service
public class UserHandler {

    @Autowired
    private UserRepository userRepository;



    public Mono<ServerResponse> getUser(ServerRequest request){

        String idStr = request.pathVariable("id");

        Long id = Long.valueOf(idStr);

        Mono<UserVo> userVoMono = userRepository.findById(id).map(this::translate);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(userVoMono,UserVo.class);

    }


    public Mono<ServerResponse> insertUser(ServerRequest request){
        Mono<User> userMonoParam = request.bodyToMono(User.class);

        Mono<UserVo> userVoMono = userMonoParam
                .cache()
                .flatMap(user -> userRepository.save(user))
                .map(this::translate);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(userVoMono,UserVo.class);
    }

    public Mono<ServerResponse> updateUser(ServerRequest request){
        Mono<User> userMonoParam = request.bodyToMono(User.class);
        Mono<UserVo> userVoMono = userMonoParam.cache()
                .flatMap(user -> userRepository.save(user))
                .map(this::translate);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(userVoMono,UserVo.class);
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request){

        String idStr = request.pathVariable("id");

        Long id = Long.valueOf(idStr);

        Mono<Void> voidMono = userRepository.deleteById(id);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(voidMono,Void.class);
    }


    public Mono<ServerResponse> findUsers(ServerRequest request){
        String userName = request.pathVariable("userName");
        String note = request.pathVariable("note");

        Flux<UserVo> userVoFlux = userRepository.findByUserNameLikeAndNoteLike(userName,note).map(this::translate);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(userVoFlux,UserVo.class);
    }


    public Mono<ServerResponse> updateUserName(ServerRequest request){
        String idStr = request.pathVariable("id");

        Long id = Long.valueOf(idStr);

        String userName = request.headers().header("userName").get(0);

        Mono<User> userMono = userRepository.findById(id);

        User user = userMono.block();

        assert user != null;
        user.setUserName(userName);

        Mono<UserVo> result = userRepository.save(user).map(this::translate);

        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(result,UserVo.class);
    }

    private UserVo translate(User user) {
        UserVo userVo = new UserVo();

        userVo.setUserName(user.getUserName());
        userVo.setSexCode(user.getSexEnum().getCode());
        userVo.setSexName(user.getSexEnum().getName());
        userVo.setNote(user.getNote());
        userVo.setId(user.getId());

        return userVo;

    }

}

