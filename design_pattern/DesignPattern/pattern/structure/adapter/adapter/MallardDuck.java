package adapter;
public class MallardDuck implements Duck {

	@Override
	public void quack() {
		System.out.println("鸭子叫");
	}

	@Override
	public void fly() {
		System.out.println("鸭子飞");
	}

}