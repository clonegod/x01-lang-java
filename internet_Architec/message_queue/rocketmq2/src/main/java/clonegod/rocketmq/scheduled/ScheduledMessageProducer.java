package clonegod.rocketmq.scheduled;
 import org.apache.rocketmq.client.producer.DefaultMQProducer;
 import org.apache.rocketmq.common.message.Message;
    
 public class ScheduledMessageProducer {
    
     public static void main(String[] args) throws Exception {
         // Instantiate a producer to send scheduled messages
         DefaultMQProducer producer = new DefaultMQProducer("Group-Producer-Scheduled");
         producer.setNamesrvAddr("192.168.1.201:9876");
         // Launch producer
         producer.start();
         int totalMessagesToSend = 10;
         for (int i = 0; i < totalMessagesToSend; i++) {
             Message message = new Message("TestTopic", ("Hello scheduled message " + i).getBytes());
             // This message will be delivered to consumer 10 seconds later.
             message.setDelayTimeLevel(3);
             // Send the message
             producer.send(message);
         }
    
         // Shutdown producer after use.
         producer.shutdown();
     }
        
 }