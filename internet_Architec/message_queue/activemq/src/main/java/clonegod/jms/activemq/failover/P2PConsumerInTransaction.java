package clonegod.jms.activemq.failover;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class P2PConsumerInTransaction {

	public static void main(String[] args) {
		/** 支持自动故障转移的broker配置 */
		String brokerURL = "failover:(tcp://192.168.1.201:61616,tcp://192.168.1.202:61616,tcp://192.168.1.203:61616)?";
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection(); // 创建连接
			conn.start();
			
			/** 创建事务会话 */
			session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); // transacted=true，表示开启事务会话
			
			Destination queue = session.createQueue("first-queue"); // 创建队列（如果队列已存在，则忽略）
			
			MessageConsumer consumer = session.createConsumer(queue); // 创建消息消费者
			
			TextMessage message = (TextMessage)consumer.receive(); // 接收消息-阻塞
			
			// 消费消息，开始处理业务逻辑
			System.out.println("message="+message.getText());
			System.out.println("seqNo:"+message.getStringProperty("seqNo"));
			
			if(session.getTransacted()) {
				session.commit(); // 消息被成功消费后，客户端对消息进行签收-MQ将标识本消费为已经消费。
			}
			session.close(); // 关闭session
		} catch (JMSException e) {
			e.printStackTrace();
			try {
				session.rollback();
			} catch (JMSException e1) {
				e1.printStackTrace();
			}
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
