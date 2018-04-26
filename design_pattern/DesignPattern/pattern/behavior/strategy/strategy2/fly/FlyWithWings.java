package strategy2.fly;

public class FlyWithWings implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println("用翅膀飞");
	}

}
