package command4;

/* The client */
public class TestCommand {
	public static void main(String[] args) {
		Computer computer = new Computer();

		// 每个命令对象在构造时都需要知道命令的接受者，以便调用其内部方法
		Command shutdown = new ShutDownCommand(computer);
		Command restart = new RestartCommand(computer);

		// Invoker，使用命令对象的入口
		Switch invoker = new Switch();

		String str = "shutdown"; // get value based on real situation

		// 根据指令的不同调用与其对应的命令对象
		if (str == "shutdown") {
			invoker.storeAndExecute(shutdown);
		} else {
			invoker.storeAndExecute(restart);
		}
	}
}