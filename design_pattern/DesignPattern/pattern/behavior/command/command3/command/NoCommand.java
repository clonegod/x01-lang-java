package command3.command;

/**
 * 空对象
 * 	将程序中盘的null的责任转移给空对象。
 *
 */
public class NoCommand implements Command {

	@Override
	public void execute() {
		System.out.println("do nothing");
	}

}
