package thread.basic;

import java.util.concurrent.*;

/**
 * ThreadDeadlock
 * <p/>
 * Task that deadlocks in a single-threaded Executor
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Test04ThreadDeadlock {
    ExecutorService exec = Executors.newSingleThreadExecutor(); // 单线程池

    public class RenderPageTask implements Callable<String> {
        public String call() throws Exception {
            Future<String> header, footer;
            header = exec.submit(new LoadFileTask("header.html"));
            footer = exec.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // Will deadlock -- task waiting for result of subtask
            return header.get() + page + footer.get(); // 线程池中只有一个线程，当前任务没有执行完成，阻塞队列中的任务永远无法执行，因此造成死锁
        }

        private String renderBody() {
            // Here's where we would actually render the page
            return "";
        }
    }
    
    
    public class LoadFileTask implements Callable<String> {
        private final String fileName;

        public LoadFileTask(String fileName) {
            this.fileName = fileName;
        }

        public String call() throws Exception {
            // Here's where we would actually read the file
            return "";
        }
    }

}
