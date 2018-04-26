-- 导出 rocketmq-transaction-balance 的数据库结构
CREATE DATABASE IF NOT EXISTS `rocketmq-transaction-balance` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `rocketmq-transaction-balance`;

-- 导出  表 rocketmq-transaction-balance.balance 结构
CREATE TABLE IF NOT EXISTS `balance` (
  `userid` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `amount` double NOT NULL,
  `version` int(11) NOT NULL,
  `update_by` varchar(50) NOT NULL,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

