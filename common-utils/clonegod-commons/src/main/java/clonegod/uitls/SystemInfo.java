package clonegod.uitls;

import java.io.File;
import java.net.URI;


public class SystemInfo {
	
	public static String getJavaInfo() {
		StringBuilder builder = new StringBuilder();
		builder.append("java.home=").append(System.getProperty("java.home")).append(getLineSeparator());
		builder.append("java.version=").append(System.getProperty("java.version")).append(getLineSeparator());
		builder.append("java.class.version=").append(System.getProperty("java.class.version")).append(getLineSeparator());
		builder.append("java.class.path=").append(System.getProperty("java.class.path")).append(getLineSeparator());
		return builder.toString();
	}
	
	/**
	 * 获取用户目录
	 * @return
	 */
	public static String getUserHome() {
		return System.getProperty("user.home");
	}
	
	/**
	 * 获取用户当前的工作目录-将定位到项目的根目录，如F:\asynclife\source\app\common
	 * @return
	 */
	public static String getUserDir() {
		return System.getProperty("user.dir");
	}
	
	/**
	 * 换行符（与操作系统相关）
	 * @return
	 */
	public static String getLineSeparator() {
		return System.getProperty("line.separator");
	}
	
	/**
	 * 文件路径分隔符（与操作系统相关）
	 * @return
	 */
	public static String getFileSeparator() {
		return System.getProperty("file.separator");
	}
	
	/**
	 * IO文件的临时存放目录
	 * @return
	 */
	public static String getTmpDir() {
		return System.getProperty("java.io.tmpdir");
	}
	
	/**
	 * Total Memory：
	 * 	 JVM申请的总内存，单位byte
	 * 	 the total amount of memory currently available for current and future objects, measured in bytes.
	 * 
	 * Free Memory：
	 * 	 JVM当前可用内存，单位byte	
	 * 	 an approximation to the total amount of memory currently available for future allocated objects, measured in bytes.
	 * 
	 * AvailableProcessors：
	 * 	 CPU处理器的核数
	 * 	 the maximum number of processors available to the virtual machine; never smaller than one
	 * @return
	 */
	public static String runtime() {
		Runtime rt = Runtime.getRuntime();
		return "Total Memory = "
        + rt.totalMemory()
        + ", Free Memory = "
        + rt.freeMemory()
        + ", AvailableProcessors = "
        + rt.availableProcessors();
	}
	
	
	
	
	/**
	 * 处理路径中包含空格的问题
	 * 	如C:\Documents and Settings路径包含空格，直接传入到URI中会出错。
	 *  正确的做法：通过API将普通文件对象转换为URI路径。
	 *  而且，会自动给文件路径前面加上协议：file:/C:/Documents%20and%20Settings/user001/logs
	 * @return
	 */
	public static URI getUserHomeURI() {
		 String home = System.getProperty("user.home");
		 File file = new File(home, "logs");  
		 URI uri = file.toURI();  
		 // file:/C:/Users/Administrator/logs
		 return uri;
	}
	
	
	public static void main(String[] args) throws Exception {
		print(getUserHomeURI().toString());
	}
	
	public static void print(String string) {
		System.out.print(string);
	}
	public static void println(String string) {
		System.out.println(string);
	}
	
}
