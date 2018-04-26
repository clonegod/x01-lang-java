package command;

public abstract class AbstractCommand implements Command {

	public abstract void execute();

	protected Target target;

	public AbstractCommand(Target target) {
		this.target = target;
	}

	public Target getTarget() {
		return target;
	}
}
