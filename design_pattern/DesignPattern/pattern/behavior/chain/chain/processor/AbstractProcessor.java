package chain.processor;

public abstract class AbstractProcessor implements Processor {

	private Processor next;
	
	@Override
	public TaskData process(TaskData task) {
		handle(task);
		// 如果当前处理器已经将任务处理完成，则不再继续调用next processor了。
		if(task.complete) {
			return task;
		}
		// 如果责任链中还有successor，则继续执行
		if(next != null) {
			next.process(task);
		}
		return task;
	}

	protected abstract TaskData handle(TaskData task);
	
	public Processor next(Processor next) {
		this.next = next;
		return next;
	}

}
