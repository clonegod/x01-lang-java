package demo.guava.files;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import com.google.common.io.ByteStreams;

public class StreamsTest {
	
	// Limiting the size of InputStreams
	@Test
	public void limitByteStreamTest() throws Exception {
		File binaryFile = new File("tmp/guava.pdf");
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(binaryFile));
		assertThat(inputStream.available(), is(1722010));
		
		InputStream limitedInputStream = ByteStreams.limit(inputStream, 10);
		assertThat(limitedInputStream.available(), is(10));
	}
	
	@Test
	public void testCharStream() {
		
	}
}
