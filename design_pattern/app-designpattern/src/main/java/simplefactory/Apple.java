package simplefactory;

public class Apple implements Fruit {
	
	private int treeAge; // 苹果为多年生植物，有树龄

	public void plant() {
		log("Apple has been planted");
	}

	public void grow() {
		log("Apple is growing...");
	}

	public void harvest() {
		log("Apple has been harvested");
	}
	
	public static void log(String msg) {
		System.out.println(msg);
	}

	public int getTreeAge() {
		return treeAge;
	}

	public void setTreeAge(int treeAge) {
		this.treeAge = treeAge;
	}
	
	

}
