package clonegod.dubbo.user.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

@Component
public class RegisterQueueMessageListener implements MessageListener{

    Logger logger= LoggerFactory.getLogger(RegisterQueueMessageListener.class);


    public void onMessage(Message message) {
        TextMessage message1=(TextMessage) message;
        try {
            logger.info(message1.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
