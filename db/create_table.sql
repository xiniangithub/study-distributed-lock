create database db_distributed_lock
default charset=utf8
default collate utf8_bin;

use db_distributed_lock;

create table t_product
(
    id int auto_increment primary key comment 'ID',
    product_name varchar(255) comment '商品名称',
    product_description varchar(255) comment '商品描述',
    price decimal(11, 2) comment '价格',
    count int comment '商品数量',
    create_user varchar(50) comment '创建用户',
    create_time timestamp comment '创建时间',
    update_user varchar(50) comment '更新用户',
    update_time timestamp comment '更新时间'
)
engine=innodb
default charset=utf8
default collate utf8_bin
comment='商品表';

insert into t_product (id, product_name, price, count, product_description, create_time, create_user, update_time, update_user)
values(100100, '测试商品', 5, 1, '测试商品', now(), 'xxx', now(), 'xxx');

create table t_order
(
    id int auto_increment primary key comment 'ID',
    order_status int(1) comment '订单状态，1待支付',
    receiver_name varchar(255) comment '收货人姓名',
    receiver_mobile varchar(11) comment '收货人手机号',
    order_amount decimal(11,2) comment '订单金额',
    create_user varchar(50) comment '创建用户',
    create_time timestamp comment '创建时间',
    update_user varchar(50) comment '更新用户',
    update_time timestamp comment '更新时间'
)
engine=innodb
default charset=utf8
default collate utf8_bin
comment='订单表';

create table t_order_item
(
    id int auto_increment primary key comment 'ID',
    order_id int comment '订单ID',
    product_id int comment '商品ID',
    purchase_price decimal(11,2) comment '购买金额',
    purchase_num int(3) comment '购买数量',
    create_user varchar(50) comment '创建用户',
    create_time timestamp comment '创建时间',
    update_user varchar(50) comment '更新用户',
    update_time timestamp comment '更新时间'
)
engine=innodb
default charset=utf8
default collate utf8_bin
comment='订单商品表';

create table t_distributed_business_lock
(
    id int auto_increment primary key,
    business_code varchar(50) not null,
    business_name varchar(50) not null
)
engine=innodb
default charset=utf8
default collate utf8_bin;