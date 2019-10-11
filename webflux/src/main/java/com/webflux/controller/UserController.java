package com.webflux.controller;

import com.webflux.pojo.User;
import com.webflux.pojo.vo.UserVo;
import com.webflux.service.UserService;
import com.webflux.utils.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 17:57
 **/

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/get_user")
    public Mono<UserVo> getUser(Long id) {

        Mono<UserVo> mono = userService.getUser(id).map(this::translate);
        return mono;
    }


    @PostMapping("/insert_user")
    public Mono<UserVo> insertUser(@Valid @RequestBody User user) {
        return userService.insertUser(user).map(this::translate);
    }

    @PutMapping("/update_user")
    public Mono<UserVo> updateUser(@RequestBody User user) {
        return userService.updateUser(user).map(this::translate);
    }

    @DeleteMapping("/delete_user")
    public Mono<Void> deleteUser(Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/find_users")
    public Flux<UserVo> findUsers(String userName, String note) {
        return userService.findUsers(userName, note).map(this::translate);
    }



    @PutMapping("/update_username")
    public Mono<UserVo> updateUserName(@RequestHeader("id") Long id,@RequestHeader("userName") String userName){

        Mono<User> userMono = userService.getUser(id);

        User user = userMono.block();
        if (user ==null){
            throw new RuntimeException("找不到信息");
        }
        user.setUserName(userName);
        return this.updateUser(user);

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


    @InitBinder
    public void initBinder(DataBinder binder)
    {
        binder.setValidator(new UserValidator());
    }
}

