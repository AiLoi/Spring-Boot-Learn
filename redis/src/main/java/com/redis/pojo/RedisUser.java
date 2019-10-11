package com.redis.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RedisUser implements Serializable {

    private Integer id;

    private String username;

    private String note;

}