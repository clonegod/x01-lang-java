package clonegod.jms.activemq.spring;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class SpringJmsListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage msg = (TextMessage) message;
			System.out.println("收到消息；" + msg.getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
