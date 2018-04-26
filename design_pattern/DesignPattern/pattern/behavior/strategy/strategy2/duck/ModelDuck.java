package strategy2.duck;

import strategy2.fly.FlyNoWay;
import strategy2.quack.Quack;

public class ModelDuck extends Duck {
	public ModelDuck() {
		flyBehavior = new FlyNoWay();
		quackBehavior = new Quack();
	}

	@Override
	public void display() {
		System.out.println("我是一只模型鸭");
	}

}
