package java8.basic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * The utility class Files was first introduced in Java 7 as part of Java NIO. 
 * 
 * The JDK 8 API adds a couple of additional methods which enables us to use functional streams with files
 *
 * Notice： close functional file streams explicitly with try/with statements
 */
public class TestFiles {

	/**
	 * Listing files
	 */
	@Test
	public void testListFile() throws IOException {
		// lists all files for the current working directory
		// then maps each path to it's string representation
		// The result is then filtered, sorted and finally joined into a string.
		try (Stream<Path> stream = Files.list(Paths.get(""))) {
		    String joined = stream
		        .map(String::valueOf) 
		        .filter(path -> !path.startsWith(".")) 
		        .sorted()
		        .collect(Collectors.joining("; "));
		    System.out.println("List: " + joined);
		}
	}
	
	/**
	 * Finding files
	 */
	@Test
	public void testFindFile() throws IOException {
		Path start = Paths.get("");
		int maxDepth = 5;
//		The method find accepts three arguments: 
//			The directory path start is the initial starting point 
//			and maxDepth defines the maximum folder depth to be searched. 
//			The third argument is a matching predicate and defines the search logic.
		try (Stream<Path> stream = Files.find(start, 
											  maxDepth, 
											  (path, attr) -> String.valueOf(path).endsWith(".java"))) {
		    String joined = stream
		        .sorted()
		        .map(path -> path.getFileName().toString())
		        .collect(Collectors.joining("; "));
		    System.out.println("Found: " + joined);
		}
	}
	
	
	/**
	 * walk files
	 */
	@Test
	public void testWalkFile() throws IOException {
		// We can achieve the same behavior by utilizing the method Files.walk. 
		// Instead of passing a search predicate this method just walks over any file.
		Path start = Paths.get("");
		int maxDepth = 5;
		//  use the stream operation filter to achieve the same behavior as in the previous example.
		try (Stream<Path> stream = Files.walk(start, maxDepth)) {
		    String joined = stream
		        .map(String::valueOf)
		        .filter(path -> path.endsWith(".java"))
		        .sorted()
		        .collect(Collectors.joining("; "));
		    System.out.println("walk(): " + joined);
		}
	}
	
	
	
	/**
	 * Reading and writing files
	 */
	@Test
	public void testReadWriteFile() throws IOException {
		/**
		 * Please keep in mind that those methods are not very memory-efficient because the whole file will be read into memory.
		 * The larger the file the more heap-size will be used.  
		 */
		// Reading text files into memory
		List<String> lines = Files.readAllLines(Paths.get("res/nashorn1.js"));
		
		// writing strings into a text file
		lines.add("print('foobar');");
		Files.write(Paths.get("res/nashorn1-modified.js"), lines);
		
	}
	
	
	/**
	 * Java 8 provides three simple ways to read the lines of a text file, making text file handling quite convenient.
	 * 
	 * 大文件读写 --- 按行读取、缓冲区
	 */
	@Test
	public void testReadWriteLargeFile() throws IOException {
		
		// As an memory-efficient alternative you could use the method Files.lines. 
		// Instead of reading all lines into memory at once, this method reads and streams each line one by one via functional streams.
		try (Stream<String> stream = Files.lines(Paths.get("res/nashorn1.js"))) {
		    stream
		        .filter(line -> line.contains("print"))
		        .map(String::trim)
		        .forEach(System.out::println);
		}
		
		// If you need more fine-grained control you can instead construct a new buffered reader:
		Path path = Paths.get("res/nashorn1.js");
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String line = null;
			while((line = reader.readLine()) != null)
				System.out.println(line);
		}
		
		
		// Buffered readers also have access to functional streams. 
		// The method lines construct a functional stream upon all lines denoted by the buffered reader:
		try (BufferedReader reader = Files.newBufferedReader(path)) {
		    long countPrints = reader
		        .lines()
		        .filter(line -> line.contains("print"))
		        .count();
		    System.out.println(countPrints);
		}
		
		
		// Or in case you want to write to a file simply construct a buffered writer instead:
		path = Paths.get("res/output.js");
		try (BufferedWriter writer = Files.newBufferedWriter(path)) {
		    writer.write("print('Hello World');");
		}
	}
	
}
