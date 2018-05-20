package baeldung.concurrentmap.ConcurrentSkipListMap;

import org.junit.Test;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class ConcurrentNavigableMapManualTest {

	/**
	 * 多线程并发添加新元素到ConcurrentSkipListMap中，由于key被排序了，因此第一个key始终是最小的
	 */
    @Test
    public void givenSkipListMap_whenAccessInMultiThreads_thenOrderingStable() throws InterruptedException {
        NavigableMap<Integer, String> skipListMap = new ConcurrentSkipListMap<>();

        updateMapConcurrently(skipListMap);

        Iterator<Integer> skipListIter = skipListMap.keySet().iterator();
        int previous = skipListIter.next();
        while (skipListIter.hasNext()) {
            int current = skipListIter.next();
            assertTrue(previous < current);
        }
    }
    
    // 4个线程并发，往NavigableMap添加新元素
    private void updateMapConcurrently(NavigableMap<Integer, String> navigableMap) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            executorService.execute(() -> {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                for (int j = 0; j < 10000; j++) {
                    navigableMap.put(random.nextInt(), "test");
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
    }

    /**
     * ConcurrentSkipListMap是线程安全的
     */
    @Test
    public void givenSkipListMap_whenNavConcurrently_thenCountCorrect() throws InterruptedException {
        NavigableMap<Integer, Integer> skipListMap = new ConcurrentSkipListMap<>();
        int count = countMapElementByPollingFirstEntry(skipListMap);
        assertEquals(10000 * 4, count);
    }
    
    /**
     * TreeMap不是线程安全的
     */
    @Test
    public void givenTreeMap_whenNavConcurrently_thenCountError() throws InterruptedException {
        NavigableMap<Integer, Integer> treeMap = new TreeMap<>();
        int count = countMapElementByPollingFirstEntry(treeMap);
        assertNotEquals(10000 * 4, count);
    }

    // 统计pollFirstEntry的可执行次数
    private int countMapElementByPollingFirstEntry(NavigableMap<Integer, Integer> navigableMap) throws InterruptedException {
        for (int i = 0; i < 10000 * 4; i++) {
            navigableMap.put(i, i);
        }
        AtomicInteger counter = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int j = 0; j < 4; j++) {
            executorService.execute(() -> {
                for (int i = 0; i < 10000; i++) {
                    if (navigableMap.pollFirstEntry() != null) {
                        counter.incrementAndGet();
                    }
                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        return counter.get();
    }

}
