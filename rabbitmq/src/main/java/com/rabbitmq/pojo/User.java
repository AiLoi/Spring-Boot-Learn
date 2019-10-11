package com.rabbitmq.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: rabbitmq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-14 15:07
 **/
@Data
public class User implements Serializable {

    private Long id;

    private String userName;

    private String note;
}

