package com.activemq.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: activemq
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-13 19:44
 **/

@Data
public class User implements Serializable {

    private Long id;

    private String userName;

    private String note;

    public User(Long id, String userName, String note) {
        this.id = id;
        this.userName = userName;
        this.note = note;
    }
}

