package clonegod.rocketmq.filter;
 import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
    
 public class FilterMessageProducer {
    
     public static void main(String[] args) throws Exception {
         // Instantiate a producer to send scheduled messages
         DefaultMQProducer producer = new DefaultMQProducer("Group-Producer-Filter");
         producer.setNamesrvAddr("192.168.1.201:9876");
         // Launch producer
         producer.start();
         int totalMessagesToSend = 10;
         for (int i = 0; i < totalMessagesToSend; i++) {
             Message message = new Message("TopicTestFilter", ("Hello scheduled message " + i).getBytes());
             // Set some properties.
             message.putUserProperty("seqNo", String.valueOf(i));
             // Send the message
             SendResult sendResult = producer.send(message);
  	       	 System.out.printf("%s%n", sendResult);
         }
    
         // Shutdown producer after use.
         producer.shutdown();
     }
        
 }