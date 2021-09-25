package com.wez.study.distribute.lock.redisson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ApplicationMain
 *
 * @Author wez
 * @Time 2021/9/24 16:23
 */
@EnableScheduling
@SpringBootApplication
public class RedissonApplicationMain8080 {

    public static void main(String[] args) {
        SpringApplication.run(RedissonApplicationMain8080.class, args);
    }

}
