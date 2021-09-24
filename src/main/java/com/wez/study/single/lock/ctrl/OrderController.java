package com.wez.study.single.lock.ctrl;

import com.wez.study.single.lock.svc.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * OrderController
 *
 * @Author wez
 * @Time 2021/9/24 12:05
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/create_order")
    public String createOrder() {
        Long id = orderService.createOrder();
        System.out.println("Order id is " + id);
        return "createOrder";
    }

}
