package com.webflux.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 17:56
 **/

@Data
public class UserVo implements Serializable {

    private Long id;

    private String userName;

    private int sexCode;

    private String sexName;

    private String note;

}

