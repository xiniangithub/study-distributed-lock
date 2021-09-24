package com.wez.study.distribute.lock.database.ctrl;

import com.wez.study.distribute.lock.database.svc.DatabaseDistributeLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基于数据库实现分布式锁
 *
 * @Author wez
 * @Time 2021/9/24 16:15
 */
@RestController
@RequestMapping("distribute_lock")
public class DatabaseDistributeLockController {

    @Autowired
    private DatabaseDistributeLockService distributeLockService;

    @GetMapping("/find_by_id")
    public void findById(@RequestParam("id") Long id) {
        distributeLockService.findById(id);
    }

}
