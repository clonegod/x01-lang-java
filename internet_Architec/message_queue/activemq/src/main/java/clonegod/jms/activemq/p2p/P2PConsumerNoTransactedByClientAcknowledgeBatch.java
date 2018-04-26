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
 *  非事务会话  + 批量消息一次性签收
 */
public class P2PConsumerNoTransactedByClientAcknowledgeBatch {

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
			
			TextMessage message = null;
			// 批量消费
			for(int i=0; i<3; i++) {
				message = (TextMessage)consumer.receive(1000); // 接收消息-阻塞
				System.out.println("message="+message.getText());
				System.out.println("seqNo:"+message.getStringProperty("seqNo"));
			}
			
			// 批量签收
			/** 客户端手动签收，确认消息已被成功消费 */
			if(session.getAcknowledgeMode() == Session.CLIENT_ACKNOWLEDGE) {
				message.acknowledge(); //本条消息以及之前的那些未被签收的消息，将被一起签收（在同一个session会话中的消息）
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
