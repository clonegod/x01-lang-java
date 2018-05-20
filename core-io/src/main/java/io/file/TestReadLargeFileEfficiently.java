package io.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

/**
 * 在内存资源有限的情况下，如何高效的读取一个大文件.
 * 比如内存只有1G，要读取10G的文件。
 *	分析：
 *		大文件一次性读取到内存，肯定会导致内存溢出。
 *	解决办法：
 *		不要一次性读取整个文件，而是每次仅读取文件的一行数据到内存中处理。
 *		由于读取的内容不需要一直在内存中保留，因此可以及时释放掉这部分内存。
 *	
 * see : http://www.baeldung.com/java-read-lines-large-file
 */
public class TestReadLargeFileEfficiently {
	
	public static void main(String[] args) throws IOException {
		TestReadLargeFileEfficiently instance = new TestReadLargeFileEfficiently();
		
		Path path = Paths.get(System.getProperty("user.dir"), "pom.xml");
		
		instance.readLargeFile(path);
		
		System.out.println("\n\n");
		
		instance.readLargeFile(path.toFile());
		
	}
	
	/**
	 * Streaming Through the File
	 * 
	 * @throws IOException 
	 */
	public void readLargeFile(Path path) throws IOException {
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
		    inputStream = new FileInputStream(path.toFile());
		    sc = new Scanner(inputStream, "UTF-8");
		    while (sc.hasNextLine()) {
		        String line = sc.nextLine();
		        System.out.println("process line: " + line);
		    }
		    // note that Scanner suppresses exceptions
		    if (sc.ioException() != null) {
		        throw sc.ioException();
		    }
		} finally {
		    if (inputStream != null) {
		        inputStream.close();
		    }
		    if (sc != null) {
		        sc.close();
		    }
		}
	}
	
	
	/**
	 * Streaming with Apache Commons IO
	 * 
	 * @throws IOException 
	 */
	public void readLargeFile(File file) throws IOException {
		try(LineIterator it = FileUtils.lineIterator(file, "UTF-8");) {
		    while (it.hasNext()) {
		        String line = it.nextLine();
		        // do something with line
		        System.out.println("process line: " + line);
		    }
		}
	}
}
