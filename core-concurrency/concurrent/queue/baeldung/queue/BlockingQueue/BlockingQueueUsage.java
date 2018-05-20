package baeldung.queue.BlockingQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Multithreaded Producer-Consumer Example
 *	多线程生产者，消费者示例
 *		生产者提供毒丸，消费者遇到毒丸时自动退出
 */
public class BlockingQueueUsage {
    public static void main(String[] args) {
        int BOUND = 10;
        int N_PRODUCERS = 4;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        int poisonPill = Integer.MAX_VALUE;
        int poisonPillPerProducer = N_CONSUMERS / N_PRODUCERS;

        // 有界队列
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(BOUND);

        for (int i = 0; i < N_PRODUCERS; i++) {
            new Thread(new NumbersProducer(queue, poisonPill, poisonPillPerProducer)).start();
        }

        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new NumbersConsumer(queue, poisonPill)).start();
        }
    }
}