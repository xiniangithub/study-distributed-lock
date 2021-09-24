package com.wez.study.single.lock.dal;

import com.wez.study.single.lock.po.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * OrderMapper
 *
 * @Author wez
 * @Time 2021/9/24 12:30
 */
@Mapper
public interface OrderMapper {
    
    void insertSelective(Order order);
    
}
