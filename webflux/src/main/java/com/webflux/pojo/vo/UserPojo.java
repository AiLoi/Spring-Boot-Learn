package com.webflux.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-22 17:22
 **/
@Data
public class UserPojo implements Serializable {


    private Long id;

    private String userName;

    private int sex;

    private String note;
}

