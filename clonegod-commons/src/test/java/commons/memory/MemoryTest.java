package commons.memory;

public class MemoryTest {
	
	// -Xms30m -Xmx30m -XshowSettings:vm
	public static void main(String args[]) {
		System.out.println("Used Memory   :  "
				+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + " bytes");
		System.out.println("Free Memory   : " + Runtime.getRuntime().freeMemory() + " bytes");
		System.out.println("Total Memory  : " + Runtime.getRuntime().totalMemory() + " bytes");
		System.out.println("Max Memory    : " + Runtime.getRuntime().maxMemory() + " bytes");
	}
}