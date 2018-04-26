package simplefactory;

public class Grap implements Fruit {
	
	private boolean seedless;

	public void plant() {
		log("Grap has been planted");
	}

	public void grow() {
		log("Grap is growing...");
	}

	public void harvest() {
		log("Grap has been harvested");
	}
	
	public static void log(String msg) {
		System.out.println(msg);
	}

	public boolean isSeedless() {
		return seedless;
	}

	public void setSeedless(boolean seedless) {
		this.seedless = seedless;
	}
	
	

}
