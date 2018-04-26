CREATE TABLE `t_order_ext` ( 
 `order_id` int(10) NOT NULL COMMENT '订单ID', 
 `user_type` int(11) NOT NULL DEFAULT '0' COMMENT '用户类型', 
 `comment` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '订单备注', 
 KEY `order_id` (`order_id`) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单扩展表' 