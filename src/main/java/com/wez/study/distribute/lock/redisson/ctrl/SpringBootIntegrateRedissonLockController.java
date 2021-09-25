package com.wez.study.distribute.lock.redisson.ctrl;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 基于SpringBoot+Redisson实现分布式锁
 *
 * @Author wez
 * @Time 2021/9/25 14:43
 */
@RestController
@RequestMapping("/springboot_integrate")
public class SpringBootIntegrateRedissonLockController {

    private static final Logger logger = LoggerFactory.getLogger(SpringBootIntegrateRedissonLockController.class);

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/redisson_lock")
    public void redissonLock() {
        logger.info("进入了方法");

        // 获取锁
        RLock lock = redissonClient.getLock("order");
        lock.lock(15, TimeUnit.SECONDS); // 加锁15s，15s后释放
        logger.info("获取了锁");

        // 模拟业务处理
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放锁
            lock.unlock();
            logger.info("释放了锁");
        }
    }

}
