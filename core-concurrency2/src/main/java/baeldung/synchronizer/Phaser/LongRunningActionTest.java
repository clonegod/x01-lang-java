package baeldung.synchronizer.Phaser;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LongRunningActionTest {

	/**
	 * 演示：
	 * 在一个Phaser上的不同阶段，可以绑定不同线程进行工作。
	 * 	phase0: 3个不同的工作线程
	 * 	phase1: 2个不同的工作线程
	 */
    @Test
    public void givenPhaser_whenCoordinateWorksBetweenThreads_thenShouldCoordinateBetweenMultiplePhases() {
        //given
        ExecutorService executorService = Executors.newCachedThreadPool();
        Phaser ph = new Phaser(1); // 初始时设置1个party，相当于主线程的注册。即主线程参与到流程控制中。
        assertEquals(0, ph.getPhase());

        //when
        executorService.submit(new LongRunningAction("thread-1", ph));
        executorService.submit(new LongRunningAction("thread-2", ph));
        executorService.submit(new LongRunningAction("thread-3", ph));

        //then
        ph.arriveAndAwaitAdvance();
        assertEquals(1, ph.getPhase());
        
        //and
        executorService.submit(new LongRunningAction("thread-4", ph));
        executorService.submit(new LongRunningAction("thread-5", ph));
        
        ph.arriveAndAwaitAdvance();
        assertEquals(2, ph.getPhase());

        ph.arriveAndDeregister();
        
        try {
			executorService.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
