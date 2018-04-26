package clonegod.jms.activemq.pubsub;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *  订阅持久消息
 *	1、客户端提交自身的ID标识，注册到MQ上
 *	2、客户端在某个topic上创建持久订阅
 *	3、客户端离线之后再次启动，也可以消费历史消息（只能消费注册后并且声明了需要持久订阅的那些消息）
 */
public class PubsubPersistConsumer1 {

	public static void main(String[] args) {
		String brokerURL = "tcp://192.168.1.201:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection(); // 创建连接
			String clientID = "PERSIST-CLIENT-1";
			conn.setClientID(clientID); /** 注册clientID到MQ */
			conn.start();
			
			/** 创建事务会话 */
			session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			
			Topic topic = session.createTopic("first-topic"); // 创建topic
			
			MessageConsumer consumer = session.createDurableSubscriber(topic, clientID); /** 创建持久消息订阅者 */
			
			// 消费消息，开始处理业务逻辑o
			TextMessage message = (TextMessage)consumer.receive(); // 接收消息-阻塞
			System.out.println("message="+message.getText());
			System.out.println("seqNo:"+message.getStringProperty("seqNo"));
			
			session.commit(); // 事务会话，需手动提交消费确认。如果没有进行消费确认，则MQ会认为当前客户端消费失败了，下次将推送同样的消息给当前客户端。
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
