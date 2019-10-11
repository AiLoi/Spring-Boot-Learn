package com.concurrency.entity.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PurchaseRecord {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private BigDecimal price;

    private Integer quantity;

    private BigDecimal sum;

    private Date purchaseDate;

    private String note;

}