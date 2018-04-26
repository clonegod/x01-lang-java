package command3.command;

import command3.receiver.Light;

/**
 * ConcreteCommand
 * 将命令封装成对象
 * 命令对象：内部封装了命令执行者
 *
 */
public class LightOnCommand implements Command {

	Light light; // 命令真正的执行者
	
	public LightOnCommand(Light light) {
		super();
		this.light = light;
	}

	@Override
	public void execute() {
		light.on();
	}

}
