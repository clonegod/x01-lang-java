package clonegod.jms.activemq.p2p;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

public class P2PProducerSync {
	public static void main(String[] args) {
		
		String brokerURL = "tcp://192.168.1.201:61616";
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		
		connectionFactory.setAlwaysSyncSend(Boolean.TRUE); /** 生产者发送同步消息 */
		
		/** 设置回执窗口大小-发送异步消息时，当消息个数达到回执窗口大小时，需要等待MQ返回消息是否发送成功 */
		/** 当MQ发送消息堆积导致无法正常接收消息时，通过windowSize来控制producer发送的异步消息*/
		connectionFactory.setProducerWindowSize(3);
		
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection(); // 创建连接
			conn.start();
			
			session = conn.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE); // 创建事务会话
			
			Destination queue = session.createQueue("first-queue"); // 创建队列（如果队列已存在，则忽略）
			
			ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue); // 创建消息生产者
			
			/** 设置消息是非持久类型消息 */
			/** 非持久类型消息默认采用异步发送模式，如果要对每条消息进行发送成功确认，则需要设置connectionFactory.setAlwaysSyncSend(Boolean.TRUE) */
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
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
