package command2;

/**
 * 抽象命令
 *	将Invoker与Receiver进行了隔离
 */
public interface Command {
	public void execute();
}
