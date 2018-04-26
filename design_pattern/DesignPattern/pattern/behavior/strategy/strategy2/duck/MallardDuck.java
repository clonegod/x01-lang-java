package strategy2.duck;

import strategy2.fly.FlyWithWings;
import strategy2.quack.Quack;

public class MallardDuck extends Duck {
	
	public MallardDuck() {
		flyBehavior = new FlyWithWings();
		quackBehavior = new Quack();
	}

	@Override
	public void display() {
		System.out.println("我是一只野鸭");
	}

}
