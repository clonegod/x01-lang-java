package baeldung.concurrentmap.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 *  ConcurrentMap中某些聚合状态方法的使用，在多线程并发时的结果可能不正确。
 *
 */
public class ConcurrentMapAggregateStatusManualTest {

    private ExecutorService executorService;
    private Map<String, Integer> concurrentMap;
    private List<Integer> mapSizes;
    private int MAX_SIZE = 100000;

    @Before
    public void init() {
        executorService = Executors.newFixedThreadPool(2);
        concurrentMap = new ConcurrentHashMap<>();
        mapSizes = new ArrayList<>(MAX_SIZE);
    }
    
    /**
     * If concurrent updates are under strict control, aggregate status would still be reliable.
     * 如果并发更新受到严格控制，则聚合状态仍将是可靠的。
     */
    @Test
    public void givenConcurrentMap_whenSizeWithoutConcurrentUpdates_thenCorrect() throws InterruptedException {
        Runnable collectMapSizes = () -> {
            for (int i = 0; i < MAX_SIZE; i++) {
                concurrentMap.put(String.valueOf(i), i); // 并发更新
                mapSizes.add(concurrentMap.size()); // 在一个线程内执行聚合方法，可以获取到正确的size
            }
        };
        Runnable retrieveMapData = () -> {
            for (int i = 0; i < MAX_SIZE; i++) {
                concurrentMap.get(String.valueOf(i));	// 该线程仅仅获取元素，不会导致size变化
            }
        };
        executorService.execute(retrieveMapData);
        executorService.execute(collectMapSizes);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        for (int i = 1; i <= MAX_SIZE; i++) {
            assertEquals("map size should be consistently reliable", i, mapSizes.get(i - 1)
                .intValue());
        }
        assertEquals(MAX_SIZE, concurrentMap.size());
    }

    /**
     * results of aggregate status methods including size, isEmpty, and containsValue 
     * are typically useful only when a map is not undergoing concurrent updates in other threads
     * 
     * 对于结果聚合的一些方法，比如size,isEmpty,containsValue等方法，只有在单线程的环境下才能正确运行。
     * 如果在多线程环境，有其它线程正在添加或删除元素，则这些聚合方法的结果是不可靠的。
     */
    @Test
    public void givenConcurrentMap_whenUpdatingAndGetSize_thenError() throws InterruptedException {
        Runnable collectMapSizes = () -> {
            for (int i = 0; i < MAX_SIZE; i++) {
                mapSizes.add(concurrentMap.size()); // 获取map的size
            }
        };
        Runnable updateMapData = () -> {
            for (int i = 0; i < MAX_SIZE; i++) {
                concurrentMap.put(String.valueOf(i), i); // 向map添加新的元素
            }
        };
        executorService.execute(updateMapData);
        executorService.execute(collectMapSizes);
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        assertNotEquals("map size collected with concurrent updates not reliable", MAX_SIZE, mapSizes.get(MAX_SIZE - 1)
            .intValue());
        assertEquals(MAX_SIZE, concurrentMap.size());
    }

}
