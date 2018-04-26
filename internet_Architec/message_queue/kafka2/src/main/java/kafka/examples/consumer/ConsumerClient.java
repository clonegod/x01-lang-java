package kafka.examples.consumer;

import kafka.examples.config.KafkaProperties;

public class ConsumerClient {
    public static void main(String[] args) {
        Consumer consumerThread1 = new Consumer(KafkaProperties.TOPIC1, "DemoGroup");
        consumerThread1.start();
        
        Consumer consumerThreadInAnotherGroup = new Consumer(KafkaProperties.TOPIC1, "DemoGroup2");
        consumerThreadInAnotherGroup.start();

    }
}
