package thread.basic;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * LogWriter
 * <p/>
 * Producer-consumer logging service with no shutdown support
 *	没有关闭功能的线程服务
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Test15LogWriter_CannotStop {
    private final BlockingQueue<String> queue;
    private final LoggerThread logger;
    private static final int CAPACITY = 1000;

    public Test15LogWriter_CannotStop(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>(CAPACITY);
        this.logger = new LoggerThread(writer);
    }

    public void start() {
        logger.start();
    }

    public void log(String msg) throws InterruptedException {
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        private final PrintWriter writer;

        public LoggerThread(Writer writer) {
            this.writer = new PrintWriter(writer, true); // autoflush
        }

        public void run() {
            try {
                while (true)
                    writer.println(queue.take());
            } catch (InterruptedException ignored) {
            } finally {
                writer.close();
            }
        }
    }
}
