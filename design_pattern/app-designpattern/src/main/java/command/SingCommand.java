package command;

public class SingCommand extends AbstractCommand {

	public SingCommand(Target target) {
		super(target);
	}

	@Override
	public void execute() {
		getTarget().sing();
	}

}
