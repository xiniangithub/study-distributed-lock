package com.wez.study.distribute.lock.database.svc;

import com.wez.study.distribute.lock.database.dal.DatabaseDistributeLockMapper;
import com.wez.study.distribute.lock.po.DistributeBusinessLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 基于数据库实现分布式锁
 *
 * @Author wez
 * @Time 2021/9/24 16:24
 */
@Service
public class DatabaseDistributeLockServiceImpl implements DatabaseDistributeLockService {

    @Autowired
    private DatabaseDistributeLockMapper distributeLockMapper;

    /**
     * 基于数据库实现分布式锁
     * <br/>select ... for update
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void findById(Long id) {
        System.out.println(Thread.currentThread().getName()+"进入方法。。。");

        DistributeBusinessLock businessLock = distributeLockMapper.findById(id);
        System.out.println(Thread.currentThread().getName()+"获取锁。。。");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"执行完成。。。");
    }

}
