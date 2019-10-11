package com.webflux.service.impl;

import com.webflux.mapper.UserRepository;
import com.webflux.pojo.User;
import com.webflux.pojo.vo.UserVo;
import com.webflux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 17:49
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<User> getUser(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Mono<User> insertUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<User> updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        return userRepository.deleteById(id);
    }

    @Override
    public Flux<User> findUsers(String userName, String note) {
        return userRepository.findByUserNameLikeAndNoteLike(userName,note);
    }


}

