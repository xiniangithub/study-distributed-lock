package com.wez.study.distribute.lock.redis.timer;

import com.wez.study.distribute.lock.redis.lock.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 发送短信定时任务
 *
 * @Author wez
 * @Time 2021/9/24 22:18
 */
@Component
public class SendMessageTimerTask {

    private static final Logger logger = LoggerFactory.getLogger(SendMessageTimerTask.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Scheduled(cron = "0/5 * * * * ?")
    public void sendMessage() {
        String key = "SendMessage";
        int expireTime = 10;
        try (RedisLock redisLock = new RedisLock(redisTemplate, key, expireTime)) {
            if (redisLock.lock()) {
                logger.info("发送短信。。。");
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
