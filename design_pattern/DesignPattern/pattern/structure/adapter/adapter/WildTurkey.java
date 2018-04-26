package adapter;
/**
 * Adaptee 需要被适配的对象 
 */
public class WildTurkey implements Turkey {

	@Override
	public void gobble() {
		System.out.println("咕咕咕");
	}

	@Override
	public void fly() {
		System.out.println("只能飞一会");
	}

}