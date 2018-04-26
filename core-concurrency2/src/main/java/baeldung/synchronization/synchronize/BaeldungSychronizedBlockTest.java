package baeldung.synchronization.synchronize;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class BaeldungSychronizedBlockTest {

	/**
	 * 同步块，锁-this
	 */
    @Test
    public void givenMultiThread_whenBlockSync() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        BaeldungSynchronizedBlocks synchronizedBlocks = new BaeldungSynchronizedBlocks();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(synchronizedBlocks::performSynchronisedTask));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, synchronizedBlocks.getCount());
    }
    
	/**
	 * 静态方法使用同步块-锁：class
	 */
    @Test
    public void givenMultiThread_whenStaticSyncBlock() throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();

        IntStream.range(0, 1000)
          .forEach(count -> service.submit(BaeldungSynchronizedBlocks::performStaticSyncTask));
        service.awaitTermination(100, TimeUnit.MILLISECONDS);

        assertEquals(1000, BaeldungSynchronizedBlocks.getStaticCount());
    }

}
