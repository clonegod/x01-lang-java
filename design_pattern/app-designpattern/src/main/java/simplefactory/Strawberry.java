package simplefactory;

public class Strawberry implements Fruit {

	public void plant() {
		log("Strawberry has been planted");
	}

	public void grow() {
		log("Strawberry is growing...");
	}

	public void harvest() {
		log("Strawberry has been harvested");
	}
	
	public static void log(String msg) {
		System.out.println(msg);
	}

}
