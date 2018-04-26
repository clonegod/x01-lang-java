package adapter;
/**
 * Adapter 对象适配器 - 通过组合目标对象来完成接口的转换，更灵活。
 */
public class TurkeyAdapterByAssemble implements Duck {

	Turkey turkey;
	
	public TurkeyAdapterByAssemble(Turkey turkey) {
		this.turkey = turkey;
	}

	@Override
	public void quack() {
		this.turkey.gobble(); // 调用目标对象完成功能，但是对外都是提供的统一接口
	}

	@Override
	public void fly() {
		for(int i=0; i<3; i++) {
			this.turkey.fly();
		}
	}

}