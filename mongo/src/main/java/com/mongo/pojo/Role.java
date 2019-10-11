package com.mongo.pojo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @program: mongo
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 16:32
 **/
@Data
@Document
public class Role implements Serializable {

    private Integer id;

    @Field("role_name")
    private String roleName;

    private String note;

}

