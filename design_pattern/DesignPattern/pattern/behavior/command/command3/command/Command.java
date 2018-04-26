package command3.command;

/**
 * 命令模式-
 * 	将运算块打包（内部组合1个命令接收者和一组动作），然后将命令对象传递出去
 * 	- 这个命令对象可以提供给某个线程池来执行；
 * 	- 这个命令对象也可以提供给某个Invoker来进行命令的执行；如遥控板RemoteController
 *
 */
public interface Command {
	public void execute();
}
