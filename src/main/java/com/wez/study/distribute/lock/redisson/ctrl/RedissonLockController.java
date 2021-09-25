package com.wez.study.distribute.lock.redisson.ctrl;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 基于Redisson实现分布式锁
 *
 * @Author wez
 * @Time 2021/9/25 14:43
 */
@RestController
public class RedissonLockController {

    private static final Logger logger = LoggerFactory.getLogger(RedissonLockController.class);

    @GetMapping("/redisson_lock")
    public void redissonLock() {
        logger.info("进入了方法");

        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        RedissonClient redisson = Redisson.create(config);

        // 获取锁
        RLock lock = redisson.getLock("order");
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
