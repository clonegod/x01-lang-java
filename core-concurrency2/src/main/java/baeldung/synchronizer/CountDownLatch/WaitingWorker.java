package baeldung.synchronizer.CountDownLatch;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class WaitingWorker implements Runnable {

    private final List<String> outputScraper;
    private final CountDownLatch readyThreadCounter;
    private final CountDownLatch callingThreadBlocker;
    private final CountDownLatch completedThreadCounter;

    WaitingWorker(final List<String> outputScraper, final CountDownLatch readyThreadCounter, final CountDownLatch callingThreadBlocker, CountDownLatch completedThreadCounter) {

        this.outputScraper = outputScraper;
        this.readyThreadCounter = readyThreadCounter;
        this.callingThreadBlocker = callingThreadBlocker;
        this.completedThreadCounter = completedThreadCounter;
    }

    @Override
    public void run() {
        // Mark this thread as read / started
        readyThreadCounter.countDown(); // 所有子线程在此等待
        try {
            callingThreadBlocker.await();	// 等待主线程countDown()，启动子线程任务
            outputScraper.add("Counted down");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            completedThreadCounter.countDown();
        }
    }
}
