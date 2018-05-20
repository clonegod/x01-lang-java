package baeldung.concurrentmap.ConcurrentHashMap;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * ConcurrentMap guarantees memory consistency on key/value operations in a multi-threading environment.
 *	ConcurrentMap提供多线程下内存一致性的保证
 */
public class ConcurrentMapMemoryConsistencyManualTest {

	/**
	 * As for ConcurrentHashMap, we can get a consistent and correct result
	 * ConcurrentHashMap底层使用CAS执行原子操作，因此是线程安全的
	 */
    @Test
    public void givenConcurrentMap_whenSumParallel_thenCorrect() throws Exception {
        Map<String, Integer> map = new ConcurrentHashMap<>();
        List<Integer> sumList = parallelSum100(map, 1000);
        assertEquals(1, sumList.stream()
            .distinct()
            .count());
        long wrongResultCount = sumList.stream()
            .filter(num -> num != 100)
            .count();
        assertEquals(0, wrongResultCount);
    }

    /**
     * 同步Hashtable可以保证线程并发的安全性，但是效率低。
     */
    @Test
    public void givenHashtable_whenSumParallel_thenCorrect() throws Exception {
        Map<String, Integer> map = new Hashtable<>();
        List<Integer> sumList = parallelSum100(map, 1000);
        assertEquals(1, sumList.stream()
            .distinct()
            .count());
        long wrongResultCount = sumList.stream()
            .filter(num -> num != 100)
            .count();
        assertEquals(0, wrongResultCount);
    }
    
    /**
     * HashMap does not provide a consistent view of what should be the present integer value, 
     * leading to inconsistent and undesirable results.
     * 普通HashMap没有内存一致性的保证，导致并发错误
     */
    @Test
    public void givenHashMap_whenSumParallel_thenError() throws Exception {
        Map<String, Integer> map = new HashMap<>();
        List<Integer> sumList = parallelSum100(map, 100);
        assertNotEquals(1, sumList.stream()
            .distinct()
            .count());
        long wrongResultCount = sumList.stream()
            .filter(num -> num != 100)
            .count();
        assertTrue(wrongResultCount > 0);
    }

    private List<Integer> parallelSum100(final Map<String, Integer> map, int executionTimes) throws InterruptedException {
        List<Integer> sumList = new ArrayList<>(executionTimes);
        for (int i = 0; i < executionTimes; i++) {
            map.put("test", 0);
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            // 4个线程并发，一共产生10个任务，每个任务循环10次
            for (int j = 0; j < 10; j++) {
                executorService.execute(() -> {
                    for (int k = 0; k < 10; k++)
                        map.computeIfPresent("test", (key, value) -> value + 1);
                });
            }
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            sumList.add(map.get("test"));
        }
        return sumList;
    }

}
