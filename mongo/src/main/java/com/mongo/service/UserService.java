package com.mongo.service;

import com.mongo.pojo.MongoUser;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import java.util.List;

/**
 * @program: mongo
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 16:36
 **/
public interface UserService {

    void saveUser(MongoUser mongoUser);

    MongoUser getUser(Integer id);

    DeleteResult deleteUser(Integer id);

    UpdateResult updateUser(Integer id,String userName,String note);

    List<MongoUser> findUser(String userName,String note,int skip,int limit);
}
