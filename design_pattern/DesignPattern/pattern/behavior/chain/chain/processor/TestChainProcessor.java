package chain.processor;

public class TestChainProcessor {
	public static void main(String[] args) {
		
		Processor header = new Process01();
		
		header.next(new Process02())
			  .next(new Process03());
		
		TaskData taskData = header.process(new TaskData());
		
		System.out.println(taskData.res);
	}
}
