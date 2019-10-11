package com.webflux.pojo.enums;

/**
 * @program: webflux
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-21 17:34
 **/


public enum  SexEnum {


    MALE(1,"男"),
    FEMALE(2,"女");

    private int code;

    private String name;

    SexEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SexEnum getSexEnum(int code)
    {
        SexEnum [] sexEnums = SexEnum.values();
        for (SexEnum item:sexEnums)
        {
            if (item.getCode()==code)
                return item;
        }

        return null;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

