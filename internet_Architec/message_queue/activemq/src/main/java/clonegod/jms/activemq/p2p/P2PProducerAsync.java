package clonegod.jms.activemq.p2p;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

public class P2PProducerAsync {
	public static void main(String[] args) {
		
		String brokerURL = "tcp://192.168.1.201:61616";
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		
		connectionFactory.setUseAsyncSend(Boolean.TRUE); /** 生产者异步发送消息 */

		/** 设置回执窗口大小-发送异步消息时，当消息个数达到回执窗口大小时，需要等待MQ返回消息是否发送成功 */
		/** 当MQ发送消息堆积导致无法正常接收消息时，通过windowSize来控制producer发送的异步消息*/
		connectionFactory.setProducerWindowSize(10);
		
		Connection conn = null;
		Session session = null;
		try {
			conn = connectionFactory.createConnection(); // 创建连接
			conn.start();
			
			session = conn.createSession(Boolean.TRUE, Session.CLIENT_ACKNOWLEDGE); // 创建事务会话
			
			Destination queue = session.createQueue("first-queue"); // 创建队列（如果队列已存在，则忽略）
			
			ActiveMQMessageProducer producer = (ActiveMQMessageProducer) session.createProducer(queue); // 创建消息生产者
			producer.setDeliveryMode(DeliveryMode.PERSISTENT); /** 设置生产者发送的消息是持久类型消息 */
			
			for(int i=0; i<3; i++) {
				TextMessage message = session.createTextMessage(); // 创建消息
				message.setText("You got a new message: msg-" + i); // 消息正文
				message.setStringProperty("seqNo", ""+i); // 附加属性
				message.setJMSDeliveryMode(DeliveryMode.PERSISTENT); /** 设置消息是持久类型消息 */
				
				// 异步发送消息到MQ，并设置异步回调
				producer.send(message, new AsyncCallback() {
					@Override
					public void onException(JMSException exception) {
						System.err.println(exception.getMessage());
					}
					@Override
					public void onSuccess() {
						System.out.println("异步发送消息成功：" + message.toString());
					}
				});
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
