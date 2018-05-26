package jvm.classloader;

/**
 *	BootstrapClassLoader	# 启动类加载器，加载路径；sun.boot.class.path
 *	ExtClassLoader			# 扩展类加载器，加载路径；java.ext.dirs
 *	AppClassLoader			# 应用类加载器，加载路径：java.class.path
 */
public class DefaultClassLoaderTest {
	public static void main(String[] args) {
		System.out.println("测试类加载器");
		
		Class<?> clazz = SomeClass.class;
		
		ClassLoader classLoader = clazz.getClassLoader();
		
		System.out.println("AppClassLoader="+classLoader); // sun.misc.Launcher$AppClassLoader@9d8643e
		System.out.println("ExtClassLoader="+classLoader.getParent()); // sun.misc.Launcher$ExtClassLoader@5d9d277e
		System.out.println("BootstrapClassLoader="+classLoader.getParent().getParent()); // null
		
	}
}