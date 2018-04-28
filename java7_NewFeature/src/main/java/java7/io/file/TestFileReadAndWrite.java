package java7.io.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import org.junit.Test;

/**
 * 文件读写
 * 	注意：读写都需要设置正确的字符编码
 * 
 */
public class TestFileReadAndWrite {
	
	@Test
	public void testReadFile() {
		Path path = Paths.get("src/main/java/clonegod/java7/Java7NewFutureTest.java");
		try (BufferedReader reader = 
				Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
			String line = null;
			while((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testReadFileOnce() throws IOException {
		Path path = Paths.get("src/main/java/clonegod/java7/Java7NewFutureTest.java");
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
		System.out.println(lines);
		
		byte[] bytes = Files.readAllBytes(path);
		System.out.println(new String(bytes, StandardCharsets.UTF_8));
	}
	
	@Test
	public void testWriteFile() {
		Path path = Paths.get("tmp/app.log");
		try (BufferedWriter writer = 
				Files.newBufferedWriter(path, StandardCharsets.UTF_8, 
						StandardOpenOption.CREATE, // Auto create if not exists 
						StandardOpenOption.WRITE)) {
			writer.write("你好");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testWriteFileOnce() throws IOException {
		Path path = Paths.get("tmp/app.log");
		Files.write(path, "你好a".getBytes(StandardCharsets.UTF_8),
				StandardOpenOption.CREATE, // Auto create if not exists 
				StandardOpenOption.WRITE);
	}
}
