package com.wez.study.single.lock.dal;

import com.wez.study.single.lock.po.OrderItem;
import org.apache.ibatis.annotations.Mapper;

/**
 * OrderItemMapper
 *
 * @Author wez
 * @Time 2021/9/24 12:32
 */
@Mapper
public interface OrderItemMapper {
    void insertSelective(OrderItem orderItem);
}
