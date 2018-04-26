package command2;

public class Test {
	public static void main(String[] args) {
		Command c = new CommandImpl(new Receiver());
		
		Invoker invoker = new Invoker();
		invoker.setCommand(c);
		
		invoker.action();
	}
}
