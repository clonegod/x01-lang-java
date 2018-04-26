package strategy2.quack;

public class Squeeze implements QuackBehavior {

	@Override
	public void quack() {
		System.out.println("吱吱叫");
	}

}
