# 超卖

1. 基于数据库锁实现
    1) 扣减库存不在程序中进行，而是通过数据库
    2) 向数据库传递库存数量，扣减1个库存，增量为-1
    3) 在数据库update语句计算库存，通过update行锁解决并发
2. 基于synchronized同步方法实现
3. 基于synchronized同步代码块实现
4. 基于ReentrantLock可重入锁实现

# 分布式锁
1. 基于数据库实现
```sql
-- 实现原理：for update
select ... for update
```
2. 基于Redis实现
```shell script
# 实现原理：setnx命令
set key value [EX seconds] [PX milliseconds] [NX|XX]
```
3. 基于Redisson实现
- Redisson
```xml
<dependency>
   <groupId>org.redisson</groupId>
   <artifactId>redisson</artifactId>
   <version>3.16.3</version>
</dependency>
```
- SpringBoot集成Redisson
```xml
<dependency>
    <groupId>org.redisson</groupId>
    <artifactId>redisson-spring-boot-starter</artifactId>
    <version>3.16.3</version>
</dependency>
```
