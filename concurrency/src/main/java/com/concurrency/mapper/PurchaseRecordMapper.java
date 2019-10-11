package com.concurrency.mapper;

import com.concurrency.entity.pojo.PurchaseRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PurchaseRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PurchaseRecord record);

    int insertSelective(PurchaseRecord record);

    PurchaseRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PurchaseRecord record);

    int updateByPrimaryKey(PurchaseRecord record);

    int concurrencyInsert(PurchaseRecord record);
}