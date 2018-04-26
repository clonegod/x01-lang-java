package com.asynclife.basic.nio;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

/**
 * Path - 文件或目录的路径
 * 	- 绝对路径
 * 	- 相对路径
 *
 */
public class NIOPathTest {
	
	/**
	 * Creating a Path Instance
	 */
	@Test
	public void testCreatePathInstance() {
		Path path = Paths.get("c:\\data\\myfile.txt");
		System.out.println(path.toUri());
	}
	
	
	/**
	 * Creating an Absolute Path
	 */
	@Test
	public void testAbsolutePath() {
		Path path = Paths.get("c:\\data\\myfile.txt");
		System.out.println(path.toAbsolutePath());
		
		Path path2 = Paths.get("/home/jakobjenkov/myfile.txt");
		System.out.println(path2.toAbsolutePath());
	}
	
	
	/**
	 * Creating a Relative Path
	 * 
	 * The . code means "current directory"
	 * The .. code means "parent directory" or "one directory up"
	 */
	@Test
	public void testRelativePath() {
		Path projects = Paths.get("d:\\data", "projects");
		System.out.println(projects.toAbsolutePath());
		
		Path file     = Paths.get("d:\\data", "projects\\a-project\\myfile.txt");
		System.out.println(file.toAbsolutePath());
		
		Path currentDir = Paths.get(".");
		System.out.println(currentDir.toAbsolutePath());
		
		Path parentDir = Paths.get("..");
		System.out.println(parentDir.toAbsolutePath());
		
		String path = "d:\\data\\projects\\a-project\\..\\another-project";
		Path parentDir2 = Paths.get(path);
		System.out.println(parentDir2.toAbsolutePath());
	}
	
	
	/**
	 * Path.normalize()
	 * 	Normalizing means that it removes all the . and .. codes in the middle of the path string, 
	 * 	and resolves what path the path string refers to
	 */
	@Test
	public void testNormalize() {
		String originalPath =
		        "d:\\data\\projects\\a-project\\..\\another-project";

		Path path1 = Paths.get(originalPath);
		System.out.println("path1 = " + path1);

		Path path2 = path1.normalize();
		System.out.println("path2 = " + path2);
	}
	
}
