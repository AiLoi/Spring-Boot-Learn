package com.mongo.service.impl;

import com.mongo.pojo.MongoUser;
import com.mongo.service.UserService;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: mongo
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 16:40
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void saveUser(MongoUser mongoUser) {

        mongoTemplate.insert(mongoUser);

//        Criteria criteria = Criteria.where("userName").is(mongoUser.getUserName()).and("note").is(mongoUser.getNote());
//
//        Query query = Query.query(criteria);
//
//       mongoTemplate.insert(criteria);
    }

    @Override
    public MongoUser getUser(Integer id) {
       //return mongoTemplate.findById(id,MongoUser.class);
       Criteria criteriaId = Criteria.where("id").is(id);
       Query queryId = Query.query(criteriaId);
       return mongoTemplate.findOne(queryId,MongoUser.class);
    }

    @Override
    public DeleteResult deleteUser(Integer id) {
        Criteria criteriaId = Criteria.where("id").is(id);
        Query queryId = Query.query(criteriaId);
        return mongoTemplate.remove(queryId);
    }

    @Override
    public UpdateResult updateUser(Integer id, String userName, String note) {
        return null;
    }

    @Override
    public List<MongoUser> findUser(String userName, String note, int skip, int limit) {
        //将用户名和备注设置为模糊查询准则
        Criteria criteria = Criteria.where("userName").regex(userName).and("note").regex(note);

        Query query = Query.query(criteria).limit(limit).skip(skip);

        //执行
        List<MongoUser> userList = mongoTemplate.find(query,MongoUser.class);

        return userList;
    }
}

