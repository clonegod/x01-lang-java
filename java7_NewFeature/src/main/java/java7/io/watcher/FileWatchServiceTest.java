package java7.io.watcher;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * 检测目录或文件的修改，在发生变化时发生一个事件通知。
 * 
 * @author clonegod@163.com
 *
 */
public class FileWatchServiceTest {
	
	static volatile boolean shutdown = false;
	
	public static void main(String[] args) {
		FileWatchServiceTest watchService = new FileWatchServiceTest();
		Path dir = FileSystems.getDefault().getPath("src/main/java");
		watchService.watchDir(dir);
	}
	
	public void watchDir(Path dir) {
		try {
			WatchService watcher = 
					FileSystems.getDefault().newWatchService();
			
			// 对指定目录注册感兴趣的事件到Watcher上
			WatchKey key = dir.register(watcher,
									StandardWatchEventKinds.ENTRY_CREATE,
									StandardWatchEventKinds.ENTRY_DELETE,
									StandardWatchEventKinds.ENTRY_MODIFY);
			
			while(! shutdown) {
				key = watcher.take();
				for(WatchEvent<?> event : key.pollEvents()) {
					@SuppressWarnings("unchecked")
					WatchEvent<Path> watchEvent = (WatchEvent<Path>) event;
					System.out.println(watchEvent.context() + ", count: " +
		                        watchEvent.count() + ", event: " + watchEvent.kind());
					switch(event.kind().name()) {
						case "ENTRY_CREATE": 
							handleCreate(watchEvent.context());
							break;
						case "ENTRY_MODIFY": 
							handleModify(watchEvent.context());
							break;
						case "ENTRY_DELETE": 
							handleDelete(watchEvent.context());
							break;
						default:
							System.out.println("Unhandled Event Occur: " + event);
							
					}
					if(event.kind() == StandardWatchEventKinds.OVERFLOW) {
						continue;
					}
				}
				key.reset(); // 重置监测key
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void handleDelete(Path path) {
		System.out.println("Process Event Deleted: " + path);		
	}

	private void handleModify(Path path) {
		System.out.println("Process Event Modified: " + path);
	}

	private void handleCreate(Path path) {
		System.out.println("Process Event Created: " + path);
	}
}
