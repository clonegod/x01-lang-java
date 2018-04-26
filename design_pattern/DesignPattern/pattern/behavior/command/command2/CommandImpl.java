package command2;

public class CommandImpl implements Command {
	
	private Receiver receiver;
	
	public CommandImpl(Receiver r) {
		this.receiver = r;
	}

	public void execute() {
		receiver.doTask();
	}

}
