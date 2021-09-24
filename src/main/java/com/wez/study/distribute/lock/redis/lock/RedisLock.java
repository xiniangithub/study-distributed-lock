package com.wez.study.distribute.lock.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.Expiration;

import java.util.Arrays;
import java.util.UUID;

/**
 * Redis分布式锁
 *
 * @Author wez
 * @Time 2021/9/24 22:04
 */
public class RedisLock implements AutoCloseable {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private StringRedisTemplate redisTemplate;
    private String key;
    private String value;
    private int expireTime;

    public RedisLock(StringRedisTemplate redisTemplate, String key, int expireTime) {
        this.redisTemplate = redisTemplate;
        this.key = key;
        this.value = UUID.randomUUID().toString();
        this.expireTime = expireTime;
    }

    public Boolean lock() {
        RedisCallback<Boolean> callback = redisConnection -> {
            // 设置NX
            RedisStringCommands.SetOption nx = RedisStringCommands.SetOption.SET_IF_ABSENT;
            // 过期时间
            Expiration expiration = Expiration.seconds(expireTime);
            // key
            byte[] keyByte = redisTemplate.getStringSerializer().serialize(key);
            // value
            byte[] valueByte = redisTemplate.getStringSerializer().serialize(value);
            // setnx
            Boolean result = redisConnection.set(keyByte, valueByte, expiration, nx);
            return result;
        };
        // 获取分布式锁
        Boolean lock = redisTemplate.execute(callback);
        return lock;
    }

    public Boolean unlock() {
        // 释放锁
        String script =
                "if redis.call(\"get\",KEYS[1]) == ARGV[1]\n" +
                        "then\n" +
                        "    return redis.call(\"del\",KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";
        RedisScript<Boolean> redisScript = RedisScript.of(script, Boolean.class);
        Boolean unlock = redisTemplate.execute(redisScript, Arrays.asList(key), value);
        logger.info("释放锁结果：{}", unlock);
        return unlock;
    }

    /**
     * try() catch时，自动关闭资源
     */
    @Override
    public void close() {
        unlock();
    }
}
