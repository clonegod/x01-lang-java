package demo.guava.basic.string;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;

import org.junit.Test;

import com.google.common.base.CharMatcher;

public class CharMatcherTest {
	// replaceFrom
	@Test
	public void testReplaceLineSepartor() throws FileNotFoundException, IOException{
		try (FileReader fileReader = new FileReader(new File("tmp/out.txt"))) {
			CharBuffer charBuffer = CharBuffer.allocate(1024);
			while((fileReader.read(charBuffer)) != -1) {
				fileReader.read(charBuffer);
				if(! charBuffer.hasRemaining()) {
					break;
				}
			}
			String oneline = CharMatcher.breakingWhitespace().replaceFrom(charBuffer.toString(), " ");
			System.out.println(oneline);
		} finally {
			
		}
	}
	
	// collapseFrom
	@Test
	public void testCollapseWhiteSpace(){
		String tabsAndSpaces = "String with 	spaces and tabs";
		String expected = "String with spaces and tabs";
		String scrubbed = CharMatcher.whitespace().collapseFrom(tabsAndSpaces, ' ');
		assertThat(scrubbed, is(expected));
	}
	
	// trimAndCollapseFrom
	@Test
	public void testTrimCollapseWhiteSpace(){
		String tabsAndSpaces = " String with spaces and tabs";
		String expected = "String with spaces and tabs";
		String scrubbed = CharMatcher.whitespace().trimAndCollapseFrom(tabsAndSpaces,' ');
		assertThat(scrubbed,is(expected));
	}
	
	// collapseFrom
	@Test
	public void testRemoveDuplicate(){
		String scrubbed = CharMatcher.anyOf("eko").collapseFrom("bookkeeper", '-');
		System.out.println(scrubbed);
	}
	
	@Test
	public void testRetainFrom(){
		String lettersAndNumbers = "foo989yxbar234";
		String expected = "989234";
		String retained = CharMatcher.javaDigit().retainFrom(lettersAndNumbers);
		assertThat(expected,is(retained));
	}
	
	@Test
	public void testMoreCharamtcher() {
		CharMatcher cm = CharMatcher.javaDigit().or(CharMatcher.whitespace());
		System.out.println(cm.countIn("as1 2"));
	}
}
