package simplefactory;

public class Client {
	public static void main(String[] args) {
		try {
			Fruit fruit = FruitFactory.factory("apple");
			
			fruit.plant();
			fruit.grow();
			fruit.harvest();
			
		} catch (BadFruitException e) {
			e.printStackTrace();
		}
	}
}
