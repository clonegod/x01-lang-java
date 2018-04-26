package command3.invoker;

import command3.command.Command;
import command3.command.NoCommand;

public class RemoteController {
	Command[] onCommands;
	Command[] offCommands;
	
	// 初始化各个命令插槽
	public RemoteController() {
		this.onCommands = new Command[7];
		this.offCommands = new Command[7];
		
		NoCommand noCommand = new NoCommand();
		for(int i=0; i<7; i++) {
			onCommands[i] = noCommand;
			offCommands[i] = noCommand;
		}
	}
	
	public void setCommand(int slot, Command onCommand, Command offCommand) {
		onCommands[slot] = onCommand;
		offCommands[slot] = offCommand;
	}
	
	public void onButtonWasPressed(int slot) {
		onCommands[slot].execute();
	}
	
	public void offButtonWasPressed(int slot) {
		offCommands[slot].execute();
	}
	
	public String toString() {
		StringBuffer stringBuf = new StringBuffer();
		stringBuf.append("\n---------------\n");
		for(int i=0; i< onCommands.length; i++) {
			stringBuf.append("[slot" + i + "] " + onCommands[i].getClass().getName()
					+ "  " + offCommands[i].getClass().getName() + "\n");
		}
		return stringBuf.toString();
	}
}
