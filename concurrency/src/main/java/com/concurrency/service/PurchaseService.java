package com.concurrency.service;

import com.concurrency.entity.pojo.PurchaseRecord;

import java.util.List;

/**
 * @program: concurrency
 * @description:
 * @create: 2019-06-23 18:07
 * @author: Ailuoli
 **/
public interface PurchaseService {

    boolean purchase(PurchaseRecord purchaseRecord);


    boolean purchaseRedis(Long userId,Long productId,int quantity);

    boolean dealRedisPurchase(List<PurchaseRecord> purchaseRecordList);

}

