package com.wez.study.single.lock.svc;

import com.wez.study.single.lock.dal.OrderItemMapper;
import com.wez.study.single.lock.dal.OrderMapper;
import com.wez.study.single.lock.po.Order;
import com.wez.study.single.lock.dal.ProductMapper;
import com.wez.study.single.lock.po.OrderItem;
import com.wez.study.single.lock.po.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * OrderServiceImpl
 *
 * @Author wez
 * @Time 2021/9/24 12:26
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private OrderMapper orderMapper;
    private int purchaseProductId = 100100;
    private int purchaseProductNum = 1;

    /**
     * 超卖现象一
     * 在程序中计算库存数量。
     * 原因：在程序中计算库存数量，无法控制库存有序减少，导致并发判断库存时，都判断库存满足
     * 解决：
     * 1. 扣减库存不在程序中进行，而是通过数据库
     * 2. 向数据库传递库存数量，扣减1个库存，增量为-1
     * 3. 在数据库update语句计算库存，通过update行锁解决并发
     * @return
     */
    /*@Transactional(rollbackFor = Exception.class)
    @Override
    public Long createOrder() {
        Product product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            throw new IllegalArgumentException("商品"+purchaseProductId+"不存在");
        }

        // 当前库存
        Integer currentCount = product.getCount();
        // 校验库存
        if (purchaseProductNum > currentCount) {
            throw new IllegalArgumentException("商品"+purchaseProductId+"仅剩"+currentCount+"件，无法购买");
        }

        // 超卖现象：在程序中计算库存，并发时会发生超卖现象
        *//*
        // 计算剩余库存
        int leftCount = currentCount - purchaseProductNum;
        // 更新库存
        product.setCount(leftCount);
        product.setUpdateTime(new Date());
        product.setUpdateUser("xxx");
        productMapper.updateByPrimaryKeySelective(product);
        *//*
        // 解决超卖现象：在数据库update语句计算库存，通过update行锁解决并发
        productMapper.updateCount(product.getId(), purchaseProductNum);
        // 依然存在超卖现象：商品数量为负数
        // 原因：在判断库存时是并发判断，各个线程都判断库存大于1

        Order order = insertSelectiveOrder(product);
        insertSelectiveOrderItem(product, order);
        return order.getId();
    }*/

    @Autowired
    private PlatformTransactionManager platformTransactionManager;
    @Autowired
    private TransactionDefinition transactionDefinition;
    /**
     * 超卖现象二
     * 原因：在并发判断库存时，各个线程都判断库存大于1，库存满足
     * 解决：
     * 1. 使用synchronized方法，将判断库存和减库存操作变成原子操作
     * 注意：
     * 在使用synchronized方法解决并发问题时，方法执行完毕锁释放，
     * 但@Transactional事务并不一定提交，所以依然存在并发问题。
     * 需要通过手动加事务的方式解决。
     * @return
     */
//    @Transactional(rollbackFor = Exception.class)
    /*@Override
    public synchronized Long createOrder() {
        // 开启事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);

        Product product = productMapper.selectByPrimaryKey(purchaseProductId);
        if (product == null) {
            platformTransactionManager.rollback(transaction); // 回滚事务
            throw new IllegalArgumentException("商品"+purchaseProductId+"不存在");
        }

        // 当前库存
        Integer currentCount = product.getCount();
        // 校验库存
        if (purchaseProductNum > currentCount) {
            platformTransactionManager.rollback(transaction); // 回滚事务
            throw new IllegalArgumentException("商品"+purchaseProductId+"仅剩"+currentCount+"件，无法购买");
        }

        productMapper.updateCount(product.getId(), purchaseProductNum);

        Order order = insertSelectiveOrder(product);
        insertSelectiveOrderItem(product, order);

        // 提交事务
        platformTransactionManager.commit(transaction);
        return order.getId();
    }*/

    /**
     * 超卖现象二
     * 原因：在判断库存时是并发判断，各个线程都判断库存大于1
     * 解决：
     * 1. 使用synchronized代码块，将判断库存和减库存操作变成原子操作
     * 注意：
     * 在使用synchronized代码块解决并发问题时，方法执行完毕锁释放，
     * 但@Transactional事务并不一定提交，所以依然存在并发问题。
     * 需要通过手动加事务的方式解决。
     * @return
     */
//    @Transactional(rollbackFor = Exception.class)
    /*@Override
    public synchronized Long createOrder() {
        Product product = null;
        synchronized (this) {
            // 开启事务
            TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);

            product = productMapper.selectByPrimaryKey(purchaseProductId);
            if (product == null) {
                platformTransactionManager.rollback(transaction); // 回滚事务
                throw new IllegalArgumentException("商品"+purchaseProductId+"不存在");
            }

            // 当前库存
            Integer currentCount = product.getCount();
            System.out.println(Thread.currentThread().getName()+"当前库存"+currentCount);
            // 校验库存
            if (purchaseProductNum > currentCount) {
                platformTransactionManager.rollback(transaction); // 回滚事务
                throw new IllegalArgumentException("商品"+purchaseProductId+"仅剩"+currentCount+"件，无法购买");
            }

            productMapper.updateCount(product.getId(), purchaseProductNum);

            // 提交事务
            platformTransactionManager.commit(transaction);
        }

        // 开启事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        Order order = insertSelectiveOrder(product);
        insertSelectiveOrderItem(product, order);

        // 提交事务
        platformTransactionManager.commit(transaction);
        return order.getId();
    }*/

    /**
     * 超卖现象二
     * 原因：在判断库存时是并发判断，各个线程都判断库存大于1
     * 解决：
     * 1. 使用ReentrantLock，将判断库存和减库存操作变成原子操作
     * 注意：
     * 在使用synchronized代码块解决并发问题时，方法执行完毕锁释放，
     * 但@Transactional事务并不一定提交，所以依然存在并发问题。
     * 需要通过手动加事务的方式解决。
     * @return
     */
    private Lock lock = new ReentrantLock();
//    @Transactional(rollbackFor = Exception.class)
    @Override
    public synchronized Long createOrder() {
        Product product = null;
        try {
            lock.lock();

            // 开启事务
            TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);

            product = productMapper.selectByPrimaryKey(purchaseProductId);
            if (product == null) {
                platformTransactionManager.rollback(transaction); // 回滚事务
                throw new IllegalArgumentException("商品"+purchaseProductId+"不存在");
            }

            // 当前库存
            Integer currentCount = product.getCount();
            System.out.println(Thread.currentThread().getName()+"当前库存"+currentCount);
            // 校验库存
            if (purchaseProductNum > currentCount) {
                platformTransactionManager.rollback(transaction); // 回滚事务
                throw new IllegalArgumentException("商品"+purchaseProductId+"仅剩"+currentCount+"件，无法购买");
            }

            productMapper.updateCount(product.getId(), purchaseProductNum);

            // 提交事务
            platformTransactionManager.commit(transaction);
        } finally {
            lock.unlock();
        }

        // 开启事务
        TransactionStatus transaction = platformTransactionManager.getTransaction(transactionDefinition);
        Order order = insertSelectiveOrder(product);
        insertSelectiveOrderItem(product, order);

        // 提交事务
        platformTransactionManager.commit(transaction);
        return order.getId();
    }

    private Order insertSelectiveOrder(Product product) {
        Order order = new Order();
        order.setOrderAmount(product.getPrice().multiply(new BigDecimal(purchaseProductNum)));
        order.setOrderStatus(1); // 待处理
        order.setReceiverName("xxx");
        order.setReceiverMobile("13311112222");
        order.setCreateUser("xxx");
        order.setCreateTime(new Date());
        order.setUpdateUser("xxx");
        order.setUpdateTime(new Date());
        orderMapper.insertSelective(order);
        return order;
    }

    private void insertSelectiveOrderItem(Product product, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderId(order.getId());
        orderItem.setProductId(product.getId());
        orderItem.setPurchasePrice(product.getPrice());
        orderItem.setPurchaseNum(purchaseProductNum);
        orderItem.setCreateUser("xxx");
        orderItem.setCreateTime(new Date());
        orderItem.setUpdateUser("xxx");
        orderItem.setUpdateTime(new Date());
        orderItemMapper.insertSelective(orderItem);
    }
}
