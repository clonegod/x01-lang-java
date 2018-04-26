package command;

import java.util.Vector;

public class MacroCommand implements Command  {
	
	private Vector<Command> commands = new Vector<Command>();
	
	public MacroCommand add(Command toAdd) {
		commands.add(toAdd);
		return this;
	}
	
	public MacroCommand remove(Command toAdd) {
		commands.remove(toAdd);
		return this;
	}
	
	public void execute() {
		for(Command c : commands) {
			c.execute();
		}
	}

}
