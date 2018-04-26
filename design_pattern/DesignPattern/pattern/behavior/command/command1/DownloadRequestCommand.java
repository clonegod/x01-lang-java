package command1;

/**
 * 每个命令对象，都有一个对应的命令执行对象
 *	DownloadRequestCommand 的执行对象就是 Downloader
 */
public class DownloadRequestCommand implements Command {

	Downloader downloader;
	
	public DownloadRequestCommand(Downloader downloader) {
		super();
		this.downloader = downloader;
	}

	@Override
	public void execute() {
		downloader.download();
	}

}
