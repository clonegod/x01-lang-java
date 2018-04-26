package clonegod.jms.activemq.failover;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class P2PProducer {
	public static void main(String[] args) {
		/** 支持自动故障转移的broker配置 */
		String brokerURL = "failover:(tcp://192.168.1.201:61616,tcp://192.168.1.202:61616,tcp://192.168.1.203:61616)?";
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection(); // 创建连接
			conn.start();
			
			session = conn.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE); // 创建事务会话
			
			Destination queue = session.createQueue("first-queue"); // 创建队列（如果队列已存在，则忽略）
			
			MessageProducer producer = session.createProducer(queue); // 创建消息生产者
			for(int i=0; i<3; i++) {
				TextMessage message = session.createTextMessage(); // 创建消息
				message.setText("You got a new message: msg-" + i); // 消息正文
				message.setStringProperty("seqNo", ""+i); // 附加属性
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
