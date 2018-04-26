package command3.invoker;

import command3.command.Command;

/**
 * Invoker
 *
 */
public class SimpleRemoteController {
	
	Command slot;
	
	public void setCommand(Command command) {
		this.slot = command;
	}
	
	public void buttonWasPressed() {
		slot.execute();
	}
}
