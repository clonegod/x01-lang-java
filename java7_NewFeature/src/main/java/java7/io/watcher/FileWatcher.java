package java7.io.watcher;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 递归监控目录/子目录下的文件变化
 * 
 * @author clonegod@163.com
 *
 */
public class FileWatcher implements Runnable {

    Logger log = Logger.getAnonymousLogger();
	private  WatchService watcher;
    private  Map<WatchKey,Path> keys;
    private  boolean recursive;
    private boolean trace = false;
    
    /**
     * Creates a WatchService and registers the given directory
     */
    public FileWatcher(Path dir, boolean recursive)  {
        try {
			this.watcher = FileSystems.getDefault().newWatchService();
			this.keys = new HashMap<WatchKey,Path>();
			this.recursive = recursive;

			if (recursive) {
			    System.out.format("Scanning %s ...\n", dir);
			    registerAll(dir);
			    System.out.println("Done.");
			} else {
			    register(dir);
			}

			// enable trace after initial registration
			this.trace = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void run() {
		processEvents();
	}

    /**
     * Register the given directory, and all its sub-directories, with the WatchService.
     */
    private void registerAll(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                throws IOException
            {
                register(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    /**
     * Register the given directory with the WatchService
     */
    private void register(Path dir) throws IOException {
    	// A key representing the registration of this object with the given watch service
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        // 将被监控的path关联到key，以便得到事件通知时获取到path
        keys.put(key, dir);
    }

    /**
     * Process all events for keys queued to the watcher
     */
    void processEvents() {
        for (;;) {

            // wait for key to be signaled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }

            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }

            for (WatchEvent<?> event: key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();

                // TBD - provide example of how OVERFLOW event is handled
                if (kind == OVERFLOW) {
                    continue;
                }

                // Context for directory entry event is the file name of entry
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                Path child = dir.resolve(name).toAbsolutePath(); // dir为父目录，name为发生事件的子目录/文件

                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);

                // if directory is created, and watching recursively, then
                // register it and its sub-directories
                if (recursive && (kind == ENTRY_CREATE)) {
                    try {
                        if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
                            registerAll(child);
                        }
                    } catch (IOException x) {
                        // ignore to keep sample readbale
                    }
                }
                
                String filePath = child.normalize().toString();
                if(kind == ENTRY_MODIFY) {
                    log.info("ENTRY_MODIFY: filePath= " + filePath);
                }
                
                if(kind == ENTRY_DELETE) {
                	System.out.println("ENTRY_DELETE: filePath= " + filePath);
                }
            }

            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }
    
    @SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }
    
    
    public static void main(String[] args) {
    	Runnable fileWatcher = new FileWatcher(Paths.get("src/main/java"), true);
		new Thread(fileWatcher).start();
	}
}