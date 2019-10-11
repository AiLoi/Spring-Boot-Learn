package com.concurrency.entity.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Product {
    private Integer id;

    private String productName;

    private Integer stock;

    private BigDecimal price;

    private Integer version;

    private String note;

}