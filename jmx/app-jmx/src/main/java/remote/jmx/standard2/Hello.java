package remote.jmx.standard2;

/**
 * 具体实现类
 *
 */
public class Hello implements HelloMBean {
	
	private String name;

	public void printHelloWorld() {
		System.out.println(name + ",welcome to this world.");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private boolean stopNow;

	public void stopRunner() {
		stopNow = true;
	}

	public boolean isStopNow() {
		return stopNow;
	}

	public void setStopNow(boolean stopNow) {
		this.stopNow = stopNow;
	}


}