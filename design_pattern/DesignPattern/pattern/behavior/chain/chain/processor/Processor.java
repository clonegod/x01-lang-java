package chain.processor;

public interface Processor {
	
	TaskData process(TaskData task);
	
	Processor next(Processor nextPcs);
}
