package clonegod.jms.activemq.p2p;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *  非事务会话  + 消息手工确认
 *  当transacted=false时，进入非事务性会话，设置acknowledgeMode才有意义。
 *  消息的确认应答模式分为以下4种：
 *  	AUTO_ACKNOWLEDGE
 *  	CLIENT_ACKNOWLEDGE	消息手动签收模式  
 *  	DUPS_OK_ACKNOWLEDGE
 *  	SESSION_TRANSACTED
 */
public class P2PConsumerNoTransactedByClientAcknowledge {

	public static void main(String[] args) {
		String brokerURL = "tcp://192.168.1.201:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection(); // 创建连接
			conn.start();
			
			/** 创建非事务会话，并指定消息应答模式为：客户端手动签收 */
			session = conn.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE); // 创建非事务会话，消息手动签收
			
			Destination queue = session.createQueue("first-queue"); // 创建队列（如果队列已存在，则忽略）
			
			MessageConsumer consumer = session.createConsumer(queue); // 创建消息消费者
			
			TextMessage message = (TextMessage)consumer.receive(); // 接收消息-阻塞
			System.out.println("message="+message.getText());
			System.out.println("seqNo:"+message.getStringProperty("seqNo"));
			
			/** 客户端手动签收，确认消息已被成功消费 */
			if(session.getAcknowledgeMode() == Session.CLIENT_ACKNOWLEDGE) {
				message.acknowledge(); // 对本条消息进行签收，确认消费成功 
			}
			
			session.close(); // 关闭session
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if(conn != null)
				try {
					conn.close(); // 释放连接
				} catch (JMSException e) {
					e.printStackTrace();
				}
		}
		
	}

}
