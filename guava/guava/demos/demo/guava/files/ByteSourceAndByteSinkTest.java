package demo.guava.files;

import java.io.File;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;

public class ByteSourceAndByteSinkTest {
	
	@Test
	public void createByteSourceFromFileTest() throws Exception {
		File f1 = new File("tmp/guava.pdf");
		
		ByteSource byteSource = Files.asByteSource(f1);
		byte[] readBytes = byteSource.read();
		
		byte[] bytes = Files.toByteArray(f1);
		
		assertThat(readBytes, is(bytes));
	}
	
	@Test
	public void testCreateFileByteSink() throws Exception {
		File dest = new File("tmp/byteSink.pdf");
		dest.deleteOnExit();
		
		ByteSink byteSink = Files.asByteSink(dest);
		
		File file = new File("tmp/guava.pdf");
		byteSink.write(Files.toByteArray(file));
		
		assertThat(Files.toByteArray(dest), is(Files.toByteArray(file)));
	}
	
	@Test
	public void copyToByteSinkTest() throws Exception {
		File dest = new File("tmp/sampleCompany.pdf");
		dest.deleteOnExit();
		
		File source = new File("tmp/guava.pdf");
		
		ByteSource byteSource = Files.asByteSource(source);
		ByteSink byteSink = Files.asByteSink(dest);
		byteSource.copyTo(byteSink);
		
		assertThat(Files.toByteArray(dest), is(Files.toByteArray(source)));
	}
}
