package facade;

public class TestCarFacade {
	public static void main(String[] args) {
		Car car = new Car();
		Key key = new Key();
		car.start(key);
	}
}
