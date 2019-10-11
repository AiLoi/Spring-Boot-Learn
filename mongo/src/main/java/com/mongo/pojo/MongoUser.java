package com.mongo.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @program: mongo
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 16:30
 **/

@Data
@Document
public class MongoUser implements Serializable {

    @Id
    private Integer id;

    @Field("user_name")
    private String userName;

    private String note;

}

