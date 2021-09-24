package com.wez.study.distribute.lock.database.svc;

/**
 * 基于数据库实现分布式锁
 *
 * @Author wez
 * @Time 2021/9/24 16:24
 */
public interface DatabaseDistributeLockService {

    void findById(Long id);
}
