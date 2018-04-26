/**
 * 运行的mq版本为rocketmq-all-4.1.0-incubating
 * Consumer报异常，貌似Broker还不支持SQL过滤？
 * 
 * Exception in thread "main" org.apache.rocketmq.client.exception.MQClientException: 
 * 	CODE: 1  
 *  DESC: The broker does not support consumer to filter message by SQL92
 * 
 * @author clonegod@163.com
 *
 */
package clonegod.rocketmq.filter;