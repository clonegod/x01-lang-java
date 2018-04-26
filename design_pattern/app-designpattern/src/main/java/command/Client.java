
package command;

public class Client {
	public static void main(String[] args) {
		
		Target target = new Target();
		
		Command c = new DanceCommand(target);
		
		Invoker invoker = new Invoker(c);
		
		invoker.action();
		
		System.out.println("=======================");
		
		Command dance = new DanceCommand(target);
		Command sing = new SingCommand(target);
		MacroCommand mc = new MacroCommand();
		mc.add(dance).add(sing);
		
		Invoker invokerNew = new Invoker(mc);
		invokerNew.action();
		
	}
}
