package com.wez.study.single.lock;

import com.wez.study.single.lock.svc.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationMainTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void concurrentOrder() throws InterruptedException {
        CountDownLatch cdl = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i=0; i<5; i++) {
            es.execute(() -> {
                try {
                    cyclicBarrier.await();
                    Long id = orderService.createOrder();
                    System.out.println("订单ID：" + id);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } finally {
                    cdl.countDown();
                }
            });
        }

        cdl.await();
        es.shutdown();
    }
}
