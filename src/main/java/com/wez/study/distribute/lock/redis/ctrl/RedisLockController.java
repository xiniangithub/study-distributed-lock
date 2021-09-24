package com.wez.study.distribute.lock.redis.ctrl;

import com.wez.study.distribute.lock.redis.lock.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于Redis实现分布式锁
 * 解决定时任务集群部署时，重复执行定时任务问题
 *
 * @Author wez
 * @Time 2021/9/24 21:39
 */
@RestController
public class RedisLockController {

    private static Logger logger = LoggerFactory.getLogger(RedisLockController.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/redis_lock")
    public void redisLock() {
        logger.info("进入方法。。。");
        String key = "RedisKey";
        int expireTime = 20;

        // 模拟执行业务
        try(RedisLock lock = new RedisLock(redisTemplate, key, expireTime)) {
            logger.info("获取到锁。。。");

            if (lock.lock()) {
                Thread.sleep(20000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("方法执行完成");
    }

}
