package com.webflux.service;

import com.webflux.pojo.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 17:47
 **/
public interface UserService {

    Mono<User> getUser(Long id);

    Mono<User> insertUser(User user);

    Mono<User> updateUser(User user);

    Mono<Void> deleteUser(Long id);

    Flux<User> findUsers(String userName,String note);
}

