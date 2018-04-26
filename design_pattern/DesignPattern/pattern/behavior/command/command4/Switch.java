package command4;

import java.util.ArrayList;
import java.util.List;

//in this example, suppose you use a switch to control computer

/* The Invoker class */
class Switch {
	// 保存历史命令，此处可扩展出组合命令的执行， 命令的撤销等
	private List<Command> history = new ArrayList<Command>();

	public Switch() {
	}

	/**
	 * 执行命令调度 =>
	 * 由于使用了命令模式，而非直接由客户端调用目标对象的方法。因此可以对命令执行进行扩展，如记录日志，统计命令执行时间，对命令排序后执行，
	 * 组合多个命令为宏命令等。
	 */
	public void storeAndExecute(Command command) {
		this.history.add(command); // optional, can do the execute only!

		System.out.println(String.format("Command start:%s", command.getClass().getSimpleName()));
		long start = System.currentTimeMillis();
		command.execute();
		long end = System.currentTimeMillis();
		System.out.println(
				String.format("Command end :%s , takes %s ms", command.getClass().getSimpleName(), end - start));
	}
}