package chain;

import command.Command;

public class SpecialHandler extends Handler {
	
	private Command command;
	
	public SpecialHandler(Command command) {
		this.command = command;
	}

	@Override
	public void handleRequest() {
		
		command.execute();
		
		if (getSuccessor() != null) {
			System.out.println("The request is passed to " + nextName());
			getSuccessor().handleRequest();
		} else {
			System.out.println("The request is handled here");
		}
	}

}
