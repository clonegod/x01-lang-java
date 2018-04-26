package strategy2.fly;

public class FlyNoWay implements FlyBehavior {

	@Override
	public void fly() {
		System.out.println("什么都不做，不会飞");
	}

}
