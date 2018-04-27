package demo.guava.files;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;

public class FileTest {
	
	@Test
	public void testCopyFile() throws IOException {
		File original = new File("tmp/out.txt");
		File copy = new File("tmp/out_copy.txt");
		Files.copy(original, copy);
	}
	
	@Test
	public void testMoveFile() throws IOException {
		File original = new File("tmp/out_copy.txt");
		File newFile = new File("tmp/out_copy2.txt");
		Files.move(original, newFile);
	}
	
	@Test
	public void readFileIntoListOfStringsTest() throws Exception{
		File file = new File("tmp/lines.txt");
		List<String> readLines = Files.readLines(file, Charsets.UTF_8);
		List<String> expectedLines = 
				Lists.newArrayList("The quick brown","fox jumps over","the lazy dog");
		assertThat(expectedLines, is(readLines));
	}
	
	@Test
	public void readLinesWithProcessor() throws Exception {
		File file = new File("tmp/books.csv");
		List<String> readLines = Files.readLines(file, Charsets.UTF_8, new ToListLineProcessor());
		List<String> expectedLines = 
				Lists.newArrayList("Being A Great Cook","Art is Fun","Be an Architect","History of Football","Gardening My Way");
		assertThat(expectedLines, is(readLines));
	}
	
	@Test
	public void testHashFile() throws IOException {
		File file = new File("tmp/books.csv");
		HashCode hashCode = Files.hash(file, Hashing.md5());
		System.out.println(hashCode);
	}
	
	@Test
	public void appendingWritingToFileTest() throws IOException {
		File file = new File("tmp/quote.txt");
		file.deleteOnExit(); // ensuring the fle is deleted when the JVM exits.
		
		String hamletQuoteStart = "To be, or not to be";
		Files.write(hamletQuoteStart,file, Charsets.UTF_8);
		assertThat(Files.toString(file,Charsets.UTF_8), is(hamletQuoteStart));
		
		String hamletQuoteEnd = ",that is the question";
		Files.append(hamletQuoteEnd,file,Charsets.UTF_8);
		assertThat(Files.toString(file, Charsets.UTF_8), is(hamletQuoteStart + hamletQuoteEnd));
		
		String overwrite = "Overwriting the file";
		Files.write(overwrite, file, Charsets.UTF_8);
		assertThat(Files.toString(file, Charsets.UTF_8), is(overwrite));
	}
}
