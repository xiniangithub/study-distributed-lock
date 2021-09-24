package com.wez.study.distribute.lock.database.dal;

import com.wez.study.distribute.lock.po.DistributeBusinessLock;
import org.apache.ibatis.annotations.Mapper;

/**
 * 基于数据库实现分布式锁
 *
 * @Author wez
 * @Time 2021/9/24 16:27
 */
@Mapper
public interface DatabaseDistributeLockMapper {

    DistributeBusinessLock findById(Long id);

}
