package clonegod.uitls;

import java.io.File;

public class PathHelper {
	public static void main(String[] args) {
		// classpath路径
		System.out.println("1:" + Thread.currentThread().getContextClassLoader().getResource(""));
		System.out.println("2:" + PathHelper.class.getClassLoader().getResource(""));
		System.out.println("3:" + ClassLoader.getSystemResource(""));
		System.out.println("5:" + PathHelper.class.getResource("/")); 
		
		System.out.println("4:" + PathHelper.class.getResource(""));//类所在的包路径
		
		System.out.println("6:" + new File("/").getAbsolutePath()); // 文件系统根路径
		
		System.out.println("7:" + System.getProperty("user.dir"));	// 工作目录-项目路径
		
		System.out.println("8:" + System.getProperty("file.encoding"));//获取文件编码
	}
}
