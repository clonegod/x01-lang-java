package remote.jmx.standard;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

public class Main {
	private static ObjectName objectName;
	private static MBeanServer mBeanServer;

	public static void main(String[] args) throws Exception {
		init();
		manage();
		
		Thread.sleep(TimeUnit.SECONDS.toMillis(600));
	}

	private static void init() throws Exception {
		ServerImpl serverImpl = new ServerImpl();
		ServerMonitorMBean serverMonitor = new ServerMonitor(serverImpl);

		objectName = ObjectName.getInstance("myStandardMBeanTest:id=ServerMonitor1");

		//mBeanServer = MBeanServerFactory.createMBeanServer();
		mBeanServer = ManagementFactory.getPlatformMBeanServer(); // 可以通过jconsole连接
		mBeanServer.registerMBean(serverMonitor, objectName);
		System.out.println(mBeanServer.getMBeanInfo(objectName));
	}

	private static void manage() throws Exception {
		System.out.println(mBeanServer.getMBeanCount());
		Long upTime = (Long) mBeanServer.getAttribute(objectName, "UpTime"); // 属性名首字母不要小写
		System.out.println(upTime);
	}
}