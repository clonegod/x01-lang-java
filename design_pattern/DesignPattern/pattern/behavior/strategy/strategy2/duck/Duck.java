package strategy2.duck;

import strategy2.fly.FlyBehavior;
import strategy2.quack.QuackBehavior;

public abstract class Duck {
	
	// 把行为想成是「一族算法」
	protected FlyBehavior flyBehavior; // 算法A
	
	protected QuackBehavior quackBehavior; // 算法B
	
	/**
	 * 在运行时想改变鸭子的行为，只要调用鸭子的setter方法就可以
	 */
	public void setFlyBehavior(FlyBehavior flyBehavior) {
		this.flyBehavior = flyBehavior;
	}
	public void setQuackBehavior(QuackBehavior quackBehavior) {
		this.quackBehavior = quackBehavior;
	}
	
	// 不同子类的外形不同，因此声明为抽象方法
	public abstract void display();

	// ======委托给行为类
	public void performFly() {
		flyBehavior.fly();
	}
	
	public void performQuack() {
		quackBehavior.quack();
	}
	
	// ======具有共性的行为则在基类中进行实现
	public void swim() {
		System.out.println("鸭子都会游泳");
	}
	
}
