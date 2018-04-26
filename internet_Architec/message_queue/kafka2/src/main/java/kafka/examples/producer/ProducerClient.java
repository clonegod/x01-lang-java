package kafka.examples.producer;

import kafka.examples.config.KafkaProperties;

public class ProducerClient {
    public static void main(String[] args) {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        isAsync = false;
        Producer producerThread = new Producer(KafkaProperties.TOPIC1, isAsync);
        producerThread.start();

    }
}
