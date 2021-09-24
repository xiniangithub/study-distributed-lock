package com.wez.study.distribute.lock.redis;

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
public class ApplicationMain8080 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain8080.class, args);
    }

}
