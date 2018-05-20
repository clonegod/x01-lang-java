package thread01.basic;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import juc.anno.GuardedBy;

/**
 * LogService
 * <p/>
 * Adding reliable cancellation to LogWriter
 *	增强的日志服务线程 - 支持关闭操作/结束日志线程
 *
 * @author Brian Goetz and Tim Peierls
 */
public class Test15LogService_Stopable {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;
    @GuardedBy("this") private boolean isShutdown;
    @GuardedBy("this") private int reservations;

    public Test15LogService_Stopable(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>();
        this.loggerThread = new LoggerThread();
        this.writer = new PrintWriter(writer);
    }

    public void start() {
        loggerThread.start();
    }

    /**
     * 向日志线程发起中断操作
     */
    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown)
                throw new IllegalStateException(/*...*/);
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        public void run() {
            try {
                while (true) {
                    try {
                    	// 当且仅当接收到关闭命令，并且待记录日志个数为0时，退出线程
                        synchronized (Test15LogService_Stopable.this) {
                            if (isShutdown && reservations == 0)
                                break;
                        }
                        String msg = queue.take();
                        synchronized (Test15LogService_Stopable.this) {
                            --reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) { /* retry */
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}

