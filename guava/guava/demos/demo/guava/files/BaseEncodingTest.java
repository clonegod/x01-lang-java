package demo.guava.files;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.BaseEncoding;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.CharSink;
import com.google.common.io.Files;

public class BaseEncodingTest {
	@Test
	public void encodeDecodeTest() throws Exception {
		File file = new File("tmp/guava.pdf");
		
		byte[] bytes = Files.toByteArray(file);
		
		BaseEncoding baseEncoding = BaseEncoding.base64();
		String encoded = baseEncoding.encode(bytes);
		
		assertThat(Pattern.matches("[A-Za-z0-9+/=]+", encoded), is(true));
		assertThat(baseEncoding.decode(encoded), is(bytes));
	}
	
	@Test
	public void encodeByteSinkTest() throws Exception{
		File file = new File("tmp/guava.pdf");
		
		File encodedFile = new File("tmp/encoded.txt");
		encodedFile.deleteOnExit();
		
		BaseEncoding baseEncoding = BaseEncoding.base64();
		CharSink charSink = Files.asCharSink(encodedFile, Charsets.UTF_8);
		ByteSink byteSink = baseEncoding.encodingSink(charSink);

		ByteSource byteSource = Files.asByteSource(file);
		byteSource.copyTo(byteSink);

		String encodedBytes = baseEncoding.encode(byteSource.read());
		assertThat(encodedBytes, is(Files.asCharSource(encodedFile, Charsets.UTF_8).read()));
	}
	
}
