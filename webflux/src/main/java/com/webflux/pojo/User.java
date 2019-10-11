package com.webflux.pojo;

import com.webflux.pojo.enums.SexEnum;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 17:32
 **/

@Data
public class User implements Serializable {


    @Id
    private Long id;

    private SexEnum sexEnum;

    @Field("user_name")
    private String userName;

    private String note;

}

