package strategy2;

import org.junit.Test;

import strategy2.duck.Duck;
import strategy2.duck.MallardDuck;
import strategy2.duck.ModelDuck;
import strategy2.fly.FlyRocketPowered;
import strategy2.quack.Squeeze;

/**
 * 『策略』――
 * 定义算法族，分别封装起来，让它们之间可以互相替换，此模式让算法的变化独立于使用算法的客户
 *
 */
public class TestFlyStrategy {
	
	@Test
	public void test() {
		Duck mallard = new MallardDuck();
		mallard.display();
		mallard.performQuack();
		mallard.performFly();
		
		System.out.println("-------------------------------");
		
		// 新增模型鸭，不会对已有的鸭子类型造成影响---开闭原则。
		Duck model = new ModelDuck();
		model.display();
		mallard.performQuack();
		model.performFly();
		
		System.out.println("-------------------------------");
		// 更换策略：使模型鸭具有火箭动力
		model.display();
		model.setQuackBehavior(new Squeeze()); // update strategy
		model.performQuack();
		model.setFlyBehavior(new FlyRocketPowered()); // update strategy
		model.performFly();
		
	}
	
	
	
}
