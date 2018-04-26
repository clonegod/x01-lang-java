package chain.processor;

public class Process01 extends AbstractProcessor {

	@Override
	protected TaskData handle(TaskData task) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[1];
		String method = String.format("[class: %s, method: %s]", ste.getClassName(), ste.getMethodName());
		
		task.setResult(this.getClass().getName(), method);
		
		return task;
	}

}
