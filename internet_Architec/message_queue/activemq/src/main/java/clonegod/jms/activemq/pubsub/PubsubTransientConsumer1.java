package clonegod.jms.activemq.pubsub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *  订阅瞬时消息
 *  	仅消费在consumer启动之后，producer所发送的那些消息
 *  由于是消息的即时消费，所以与会话是否为事务类型，以及消息确认模式都没关系。
 */
public class PubsubTransientConsumer1 {

	public static void main(String[] args) {
		String brokerURL = "tcp://192.168.1.201:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection(); // 创建连接
			conn.start();
			
			/** 创建事务会话 */
			session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			Destination topic = session.createTopic("first-topic"); // 创建topic
			
			MessageConsumer consumer = session.createConsumer(topic); // 创建消息订阅者
			
			// 消费消息，开始处理业务逻辑
			for(int i=0; i<3; i++) {
				TextMessage message = (TextMessage)consumer.receive(); // 接收消息-阻塞
				System.out.println("message="+message.getText());
				System.out.println("seqNo:"+message.getStringProperty("seqNo"));
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
