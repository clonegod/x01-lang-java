package remote.jmx.standard2;

/**
 * 将需要管理的属性和方法暴露为接口
 * 1. 暴露属性：通过get/set方法
 * 2. 暴露方法：声明接口操作
 */
public interface HelloMBean {
	
	public void printHelloWorld();

	public String getName();

	public void setName(String name);
	
	// 另一个线程读取HelloMBean的属性，控制运行或停止
	public void stopRunner(); // 如果已开启，则停止
	
}