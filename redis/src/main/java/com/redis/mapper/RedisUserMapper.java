package com.redis.mapper;

import com.redis.pojo.RedisUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RedisUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RedisUser record);

    int insertSelective(RedisUser record);

    RedisUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RedisUser record);

    int updateByPrimaryKey(RedisUser record);
}