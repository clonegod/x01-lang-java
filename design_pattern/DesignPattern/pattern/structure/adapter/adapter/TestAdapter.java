package adapter;
public class TestAdapter {
	public static void main(String[] args) {
		
		MallardDuck duck = new MallardDuck();
		
		// 被适配对象
		WildTurkey turkey = new WildTurkey();
		
		// 对象适配器-组合
		Duck turkeyAdapterByAssemble = new TurkeyAdapterByAssemble(turkey);
		
		// 类适配器-继承
		Duck turkeyAdapterByExtends = new TurkeyAdapterByExtends();
		
		System.out.println("The Turkey says...");
		turkey.gobble();
		turkey.fly();
		
		System.out.println("\nThe Duck says...");
		duck.quack();
		duck.fly();
		
		System.out.println("\nThe TurkeyAdapterByAssemble says...");
		turkeyAdapterByAssemble.quack();
		turkeyAdapterByAssemble.fly();
		
		System.out.println("\nThe TurkeyAdapterByExtends says...");
		turkeyAdapterByExtends.quack();
		turkeyAdapterByExtends.fly();
		
	}
}