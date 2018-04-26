package chain;

import command.Command;
import command.DanceCommand;
import command.MacroCommand;
import command.SingCommand;
import command.Target;

public class Client {
	public static void main(String[] args) {
		
		/**
		 * 命令模式
		 */
		Target receiver = new Target();
		Command dance = new DanceCommand(receiver);
		Command sing = new SingCommand(receiver);
		MacroCommand mc = new MacroCommand();
		mc.add(dance).add(sing);
		
		/**
		 * 责任链模式
		 */
		Handler h1 = new ConcretHandler1();
		Handler h2 = new SpecialHandler(mc);
		Handler h3 = new ConcretHandler3();
		h1.setSuccessor(h2);
		h2.setSuccessor(h3);
		
		h1.handleRequest();
	}
}
