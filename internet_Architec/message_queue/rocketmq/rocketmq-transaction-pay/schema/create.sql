-- 导出 rocketmq-transaction-pay 的数据库结构
CREATE DATABASE IF NOT EXISTS `rocketmq-transaction-pay` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rocketmq-transaction-pay`;

-- 导出  表 rocketmq-transaction-pay.pay 结构
CREATE TABLE IF NOT EXISTS `pay` (
  `userid` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `amount` double NOT NULL,
  `detail` varchar(50) NOT NULL,
  `update_by` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

