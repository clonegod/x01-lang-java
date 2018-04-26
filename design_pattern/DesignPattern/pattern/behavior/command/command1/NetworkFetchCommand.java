package command1;

/**
 * 每个命令对象，都有一个对应的命令执行对象
 *	NetworkFetchCommand 的执行对象就是 NetworkFetcher
 */
public class NetworkFetchCommand implements Command {

	NetworkFetcher fetcher;
	
	public NetworkFetchCommand(NetworkFetcher fetcher) {
		super();
		this.fetcher = fetcher;
	}

	@Override
	public void execute() {
		fetcher.fetch();
	}

}
