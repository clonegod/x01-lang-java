package command3.command;

/**
 * 宏命令
 *	将若干相关命令组合在一起，1次性执行完成
 */
public class MacroCommand implements Command {
	
	Command[] commands;
	
	public MacroCommand(Command[] commands) {
		this.commands = commands;
	}

	@Override
	public void execute() {
		for(int i=0; i<commands.length; i++) {
			commands[i].execute();
		}
	}

}
