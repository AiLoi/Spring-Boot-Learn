package com.concurrency.controller;

import com.concurrency.entity.pojo.PurchaseRecord;
import com.concurrency.service.PurchaseService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: concurrency
 * @description:
 * @author: Ailuoli
 * @create: 2019-06-23 18:40
 **/
@RestController
@RequestMapping("/concurrency")
public class PurchaseController {


    @Autowired
    private PurchaseService purchaseService;


    @PostMapping(value = "/purchase")
    public Result purchase(@RequestBody PurchaseRecord purchaseRecord){

        boolean success = purchaseService.purchase(purchaseRecord);

        String message = success?"抢购成功":"抢购失败";

        return new Result(success,message);
    }



    @Data
    class Result{

        private boolean success = false;

        private String message = null;

        Result(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
    }

}

