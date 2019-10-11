package com.webflux.mapper;

import com.webflux.pojo.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 17:40
 **/

@Repository
public interface UserRepository extends ReactiveMongoRepository<User,Long> {

    /**
     * 对用户名和备注进行模糊查询
     * @param userName 用户名
     * @param note 备注
     * @return 符合条件的用户
     */
    Flux<User> findByUserNameLikeAndNoteLike(String userName, String note);



}
