package clonegod.java7.io.file;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map.Entry;

import org.junit.Test;

/**
 * 文件和目录的新表示方式
 * 
 */
public class PathsTest {
	
	@Test
	public void testSystemProps() {
		String workdir = System.getProperty("user.dir");
		System.out.println(workdir);
		
		System.err.println("=================All Sys Props==================");
		
		Iterator<Entry<Object, Object>> iter = System.getProperties().entrySet().iterator();
		while(iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
	
	/**
	 * 创建Path的几种方式 
	 */
	@Test
	public void testCreatePath() throws URISyntaxException {
		Path path1 = Paths.get(new URI("file:///repos/org/apache"));
		Path path2 = Paths.get("/repos/org/apache");
		Path path3 = Paths.get("/repos", "org", "apache");
		Path path4 = FileSystems.getDefault().getPath("/repos/org/apache");
		Path path5 = Paths.get(".").toAbsolutePath().normalize();
		
		System.out.println(path1);
		System.out.println(path2);
		System.out.println(path3);
		System.out.println(path4);
		System.out.println(path5);
	}
	
	/**
	 * 通过Path获取目标文件的相关信息
	 */
	@Test
	public void testGetInfoFromPath() {
		Path listing = Paths.get("/repos/org/apache");
		System.out.println("File Name: " + listing.getFileName());
		System.out.println("Number of Name in the Path(目录层级？): " + listing.getNameCount());
		System.out.println("Parent Paht: " + listing.getParent());
		System.out.println("Root Path: " + listing.getRoot());
		System.out.println("Sub Path From Root, from 1 to 2: " + listing.subpath(1, 3));
	}
	
	/**
	 * 移除Path中的冗余项： "." and ".." 
	 * 
	 * 调用normalize前，需要对Path进行toAbsolutePath
	 */
	@Test
	public void testRemoveRedundancy() {
		Path path1 = Paths.get("../java7/test/input.txt");
		System.out.println(path1);
		
		Path path2 = path1.toAbsolutePath();
		System.out.println(path2);
		
		Path path3 = path2.normalize();
		System.out.println(path3);
	}
	
	/**
	 * 合并两个Path---基准路径 + 子路径
	 * 比如：
	 * 	基准路径是固定的/target
	 * 	子路径通过参数方式传入，可以是dev/app.conf，test/app.conf等
	 * 
	 * 通过resolve可将上面的两个路径组成一个完整的文件路径
	 * 	/target/dev/app.conf
	 *  /target/test/app.con
	 */
	@Test
	public void testConcatPath() {
		Path base = Paths.get("/tmp"); 
		String sub = "conf/application.properties"; 
		Path completePath = base.resolve(sub);
		System.out.println(completePath.toString());
	}
	
	/**
	 * Path 与 File 类的互转
	 */
	@Test
	public void testConvertBetweenFileAndPath() {
		File file = new File("tmp/input.txt");
		
		// file to path
		Path filePath = file.toPath();
		Path absPath = filePath.toAbsolutePath();
		
		System.out.println(absPath);
		
		// path to file
		File file2 = filePath.toFile();
		System.out.println(file2.getAbsolutePath());
		
	}
	
}

