package jvm.classloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

public class CustomClassLoaderTest {
	
	public static void main(String[] args) throws Exception {
		System.out.println("测试使用自定义类加载器加载class文件");
		
		MyClassLoader myLoader = new MyClassLoader("E:/source/practice-java/jvm/target/classes");
		Class<?> clazz = myLoader.loadClass("jvm.classloader.SomeClass");
		
		System.out.println("===== ClassLoaderTree =====");
		
		ClassLoader cl = clazz.getClassLoader();
		while(cl != null) {
			System.out.println(cl);
			cl = cl.getParent();
		}
		
		clazz.newInstance(); // 创建对象实例，在构造对象时static代码块被执行
	}
	
	/**
	 * 自定义类加载器：先加载指定路径下的类，当加载失败后，再使用jvm中的类加载策略。
	 * 
	 * 	1. 要加载一个类，首先得知道类的字节码文件在哪里---即文件路径。
	 *  2. 读取字节码文件到内存中，通过defineClass将byte[]转换为Class对象。
	 *  3. 如果自定义的ClassLoader加载某个类失败了，则使用默认的类加载策略来加载（AppClassLoader->ExtensionClassLoader->BootstrapClassLoader）。
	 *
	 */
	static class MyClassLoader extends ClassLoader {
		
		String classesDirecotry; // class文件所在目录
		
		public MyClassLoader(String classDir) {
			super();
			this.classesDirecotry = classDir;
		}

		/**
		 * 复写loadClass对类的加载策略，这样就可以使用自定义的逻辑来加载类了。
		 * 	比如，可以实现类的加载首先让AppClassLoader进行加载，而不是委托给parent进行加载。
		 */
		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			// First, check if the class has already been loaded
            Class<?> c = findClass(name);
			if(c != null) {
				return c;
			}
			
			System.err.println("===> 加载class失败，使用jvm默认的加载器进行加载: " + name);
			
			return super.loadClass(name);
		}

		/**
		 * 1. 根据路径读取目标类文件
		 * 2. 调用defineClass生成对应的Class对象
		 * 
		 */
		@Override
		protected Class<?> findClass(String className) throws ClassNotFoundException {
			// 检查类是否已经被加载过---1个类只被对应的类加载器加载1次
			Class<?> c = super.findLoadedClass(className);
			if(c == null) {
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(new File(this.classesDirecotry, className.replace('.', File.separatorChar) + ".class"));
					FileChannel fc = fis.getChannel();
					
					ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
					WritableByteChannel writeChannel = Channels.newChannel(out);
					
					fc.transferTo(0, fc.size(), writeChannel);
					fis.close();
					
					byte[] bytes = out.toByteArray();
					
					c = super.defineClass(null, bytes, 0, bytes.length);
					
				} catch (FileNotFoundException e) {
					// 当加载jdk中的类时，会出现异常。因为给定路径下并不存在这些类，比如Object.class是在rt.jar中。
					System.err.println("类文件不存在: "+ className);
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} 
			}
			return c;
		}
		
	}
	
	
	
}
