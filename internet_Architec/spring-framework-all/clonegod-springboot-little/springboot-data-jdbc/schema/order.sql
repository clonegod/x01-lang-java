CREATE TABLE `t_order` ( 
 `order_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '订单ID', 
 `express_type` tinyint(1) unsigned NOT NULL COMMENT '快递方式', 
 `user_id` int(10) unsigned DEFAULT NULL COMMENT '用户ID', 
 `add_time` int(10) NOT NULL COMMENT '下单时间', 
 PRIMARY KEY (`order_id`), 
 KEY `user_id` (`user_id`), 
 KEY `express_type` (`express_type`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单记录表' 