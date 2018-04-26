package clonegod.jms.activemq.retries;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQQueue;

public class P2PConsumerRetryPolicy2 {

	public static void main(String[] args) {
		/** 支持自动故障转移的broker配置 */
		String brokerURL = "failover:(tcp://192.168.1.201:61616,tcp://192.168.1.202:61616,tcp://192.168.1.203:61616)?";
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		ActiveMQConnection conn = null;
		Session session = null;
		try {
			conn = (ActiveMQConnection) connectionFactory.createConnection(); // 创建连接
			
			/** 客户端消费失败后，MQ会根据配置的重试策略重新推送消息给client端，直到消息进入DLQ */
			RedeliveryPolicy queuePolicy = new RedeliveryPolicy();
			queuePolicy.setInitialRedeliveryDelay(1000);
			queuePolicy.setRedeliveryDelay(2000);
			queuePolicy.setUseExponentialBackOff(false); // false，关闭衰减重试。则每次重试的时间间隔是相同的
			queuePolicy.setMaximumRedeliveries(5);
			
			RedeliveryPolicyMap map = conn.getRedeliveryPolicyMap();
			map.put(new ActiveMQQueue(">"), queuePolicy);
			
			conn.start();
			
			/** 创建事务会话 */
			session = conn.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE); // transacted=true，表示开启事务会话
			
			Destination queue = session.createQueue("first-queue"); // 创建队列（如果队列已存在，则忽略）
			
			MessageConsumer consumer = session.createConsumer(queue); // 创建消息消费者
			consumer.setMessageListener(new MsgHandler(session));
			
			System.in.read();
			
			session.close();
		} catch (Exception e) {
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
	
	private static class MsgHandler implements MessageListener {
		Session session;
		
		public MsgHandler(Session session) {
			super();
			this.session = session;
		}
		
		@Override
		public void onMessage(Message message) {
			try {
				TextMessage textMsg = (TextMessage)message;
				String now = new SimpleDateFormat("HH:mm:ss").format(new Date());	
				System.out.println(now + "--->message="+textMsg.getText());
				Integer.parseInt("模拟消息消费发送异常");
				
				session.commit();
			} catch (Exception e) {
				System.err.println("消费失败：" + e.getMessage());
				try {
					session.rollback();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
			}
			
		}
		
	}

}
