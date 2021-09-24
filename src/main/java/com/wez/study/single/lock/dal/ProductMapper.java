package com.wez.study.single.lock.dal;

import com.wez.study.single.lock.po.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * ProductRepository
 *
 * @Author wez
 * @Time 2021/9/24 11:37
 */
@Mapper
public interface ProductMapper {

    List<Product> findAll();

    Product selectByPrimaryKey(int purchaseProductId);

    void updateByPrimaryKeySelective(Product product);

    int updateCount(Long id, Integer count);
}
