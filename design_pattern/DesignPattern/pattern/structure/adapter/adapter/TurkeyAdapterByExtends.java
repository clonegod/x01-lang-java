package adapter;
/**
 * Adapter 类适配器 - 好处：不用重新实现被适配者的方法，甚至可以覆盖被适配者的行为，因为通过继承来完成的。
 */
public class TurkeyAdapterByExtends extends WildTurkey implements Duck {

	@Override
	public void quack() {
		super.gobble(); // 调用目标对象完成功能，但是对外都是提供的统一接口
	}

	@Override
	public void fly() {
		for(int i=0; i<3; i++) {
			super.fly();
		}
	}

}