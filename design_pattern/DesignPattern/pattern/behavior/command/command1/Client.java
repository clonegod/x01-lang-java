package command1;

public class Client {
	public static void main(String[] args) throws Exception {
		
		// Receiver - 真正处理命令的对象
		NetworkFetcher fetcher = new NetworkFetcher();
		Downloader downloader = new Downloader();
		
		// Command
		Command networkCommand = new NetworkFetchCommand(fetcher);
		Command downloadCommand = new DownloadRequestCommand(downloader);
		
		// CommandQueue
		CommandQueue queue = new CommandQueue();
		queue.addCommand(networkCommand);
		queue.addCommand(downloadCommand);
		
		// Command Executor
		Worker worker = new Worker(queue);
		worker.doWork();
		worker.shutdown();
	}
}
