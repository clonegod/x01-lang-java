package clonegod.jms.activemq.pubsub;

import java.util.Random;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PubsubProducer {
	public static void main(String[] args) {
		
		String brokerURL = "tcp://192.168.1.201:61616";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection(); // 创建连接
			conn.start();
			
			session = conn.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE); // 创建事务会话
			
			Destination topic = session.createTopic("first-topic"); // 创建topic
			
			MessageProducer producer = session.createProducer(topic); // 创建消息发布者
			for(int i=0; i<3; i++) {
				TextMessage message = session.createTextMessage(); // 创建消息
				message.setText("You got a new message: msg-" + i); // 消息正文
				message.setStringProperty("seqNo", ""+new Random().nextInt(9999999)); // 附加属性
				producer.send(message); // 发送消息到MQ
				System.out.println("消息发送成功");
			}
			
			session.commit(); /** 提交消息 - transacted 事务消息，必须进行提交，否则不会发送到MQ */
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
