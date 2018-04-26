package command;

public class DanceCommand extends AbstractCommand {

	public DanceCommand(Target target) {
		super(target);
	}

	@Override
	public void execute() {
		getTarget().dance();
	}

}
