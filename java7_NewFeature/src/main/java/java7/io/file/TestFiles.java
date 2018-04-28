package java7.io.file;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

import org.junit.Test;

public class TestFiles {
	
	/**
	 * 在目录下查找文件
	 */
	@Test
	public void testFindFileUnderSpecifyDir() {
		Path dir = Paths.get("src/main/java/java7");
		try (DirectoryStream<Path> stream = 
				Files.newDirectoryStream(dir, "*.java")) {
			for(Path entry : stream) {
				System.out.println(entry.getFileName());
			}
		} catch (IOException e) {
			System.err.println(e);
		}
	}
	
	/**
	 * Files.walkFileTree	遍历目录树
	 */
	@Test
	public void testLoopDirectory() throws IOException {
		Path startingDir = Paths.get("src/main/java");
		Files.walkFileTree(startingDir, new FindJavaVisitor());
	}
	
	/**
	 * 扩展SimpleFileVisitor<Path>
	 *
	 */
	private static class FindJavaVisitor extends SimpleFileVisitor<Path> {
		/** 重写访问文件的处理逻辑 */
		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if(file.toString().endsWith(".java")) {
				System.out.println(file.getFileName());
			}
			return FileVisitResult.CONTINUE;
		}
		
	}
	
	@Test
	public void testCreateFile() throws IOException {
		Path target = Paths.get("tmp/input.txt");
		if(target.toFile().exists()) {
			Files.delete(target);
		}
		Path path = Files.createFile(target);
		System.out.println(path);
	}
	
	@Test
	public void testCreateFileWithAttributeOfPosixSystem() throws IOException {
		Path target = Paths.get("tmp/mystuffWithPerms.txt");
		
		Set<PosixFilePermission> perms = 
				PosixFilePermissions.fromString("rw-rw-rw");
		FileAttribute<Set<PosixFilePermission>> attrs = 
				PosixFilePermissions.asFileAttribute(perms);
		
		Files.createFile(target, attrs);
	}
	
	@Test
	public void testCopyFileForce() throws IOException {
		Path source = Paths.get("tmp/input.txt");
		Path target = Paths.get("tmp/output.txt");
		// 如果目标文件已存在，则替换掉。
		Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
	}
	
	@Test
	public void testMoveFile() throws IOException {
		Path source = Paths.get("tmp/input.txt");
		Path target = Paths.get("tmp/output.txt");
		// 如果目标文件已存在，则替换掉。
		Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
	}
	
	@Test
	public void testFileAttributes() throws IOException {
		Path path = Paths.get("src/main/java");
		System.out.println(Files.getLastModifiedTime(path));
		System.out.println(Files.size(path));
		System.out.println(Files.isSymbolicLink(path));
		System.out.println(Files.isDirectory(path));
		System.out.println(Files.isRegularFile(path));
		System.out.println(Files.readAttributes(path, "*"));
	}
}
