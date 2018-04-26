package demo.guava.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.common.io.Closer;

/**
 * Closer类似 JDK7中的 try-with-resources
 * 
 * The Closer class emulates the behavior found with Java 7's try-with-resources idiom, 
 * but can be used in a Java 6 environment.
 * 
 * use the Closer class to manage the closing of the underlying I/O resources
 *  
 * it's better to use the Sources and Sinks objects than raw I/O streams, readers, or writers.
 *  
 * @author clonegod@163.com
 *
 */
public class CloserTest {
	public static void main(String[] args) throws IOException {
		Closer closer = Closer.create();
		try {
			File destination = new File("tmp/copy.txt");
			destination.deleteOnExit();
			
			BufferedReader reader = new BufferedReader(new FileReader("tmp/out.txt"));
			BufferedWriter writer = new BufferedWriter(new FileWriter(destination));
			
			closer.register(reader);
			closer.register(writer);
			
			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
			}
		} catch (Throwable t) {
			throw closer.rethrow(t);
		} finally {
			closer.close();
		}
	}
}
