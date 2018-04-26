package command3.command;

public class MarcoCommand implements Command {

	Command[] commands;
	
	public MarcoCommand(Command[] commands) {
		super();
		this.commands = commands;
	}

	@Override
	public void execute() {
		for(int i=0; i<commands.length; i++) {
			commands[i].execute();
		}
	}

}
