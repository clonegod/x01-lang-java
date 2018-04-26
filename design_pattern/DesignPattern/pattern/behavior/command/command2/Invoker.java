package command2;

/**
 * 命令发出者
 */
public class Invoker {
	
	Command command;

	public void action() {
		command.execute(); // 只关心发出命令，不关心命令如何得到执行
	}

	public void setCommand(Command command) {
		this.command = command;
	}
	
}
