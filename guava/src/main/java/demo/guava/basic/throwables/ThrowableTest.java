package demo.guava.basic.throwables;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import com.google.common.base.Throwables;

public class ThrowableTest {
	
	ExecutorService executor = Executors.newSingleThreadExecutor();
	
	/**
	 * The Throwables.getCausalChain method returns a list of Throwable instances
starting with the top level Throwable instance followed by the nested Throwable
instances in the chain. 
	 */
	@Test
	public void testGetCausalChain() {
		List<Throwable> throwables = null;
		Callable<FileInputStream> fileCallable = new Callable<FileInputStream>() {
			@Override
			public FileInputStream call() throws Exception {
				// purposely going to cause a FileNotFoundException. 
				return new FileInputStream("Bogus file");
			}
		};
		Future<FileInputStream> fisFuture = executor.submit(fileCallable);
		try {
			fisFuture.get();
		} catch (Exception e) {
			// get the causal chain of the exception hierarchy as a list from the Throwables.
			throwables = Throwables.getCausalChain(e);
		}
		assertThat(throwables.get(0).getClass().isAssignableFrom(ExecutionException.class), is(true));
		assertThat(throwables.get(1).getClass().isAssignableFrom(FileNotFoundException.class), is(true));
		executor.shutdownNow();
	}
	
	@Test
	public void testGetRootCause() throws Exception {
		Throwable cause = null;
		Callable<String> stringCallable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				throw new RuntimeException("Unable to assign a customer id");
			}
		};
		Future<String> stringFuture= executor.submit(stringCallable);
		try {
			stringFuture.get();
		} catch (Exception e) {
			cause = Throwables.getRootCause(e);
		}
		assertThat(cause.getClass().isAssignableFrom(NullPointerException.class), is(true));
		assertThat("Unable to assign a customer id", is(cause.getMessage()));
		executor.shutdownNow();
	}
}
