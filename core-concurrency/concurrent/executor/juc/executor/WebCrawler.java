package juc.executor;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import juc.anno.GuardedBy;

/**
 * WebCrawler
 * <p/>
 * Using TrackingExecutorService to save unfinished tasks for later execution
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class WebCrawler {
	
    private volatile TrackingExecutor exec; // 可追踪任务
    
    @GuardedBy("this") private final Set<URL> urlsToCrawl = new HashSet<URL>(); // 爬虫任务队列

    private final ConcurrentMap<URL, Boolean> seen = new ConcurrentHashMap<URL, Boolean>(); // 记录url是否已经被爬过
    private static final long TIMEOUT = 500;
    private static final TimeUnit UNIT = MILLISECONDS;

    /**
     * 爬虫开始的第一个页面url
     * 
     * @param startUrl
     */
    public WebCrawler(URL startUrl) {
        urlsToCrawl.add(startUrl);
    }

    /**
     * 启动爬虫
     */
    public synchronized void start() {
        exec = new TrackingExecutor(Executors.newCachedThreadPool());
        for (URL url : urlsToCrawl) submitCrawlTask(url);
        urlsToCrawl.clear();
    }

    /**
     * 结束爬虫线程池
     * 1. 将showdownNow()返回的未执行任务添加到爬虫队列中；
     * 2. 将那些正在运行但被showdownNow()中断的任务加入到爬虫队列中；
     * 
     * @throws InterruptedException
     */
    public synchronized void stop() throws InterruptedException {
        try {
            saveUncrawled(exec.shutdownNow());
            if (exec.awaitTermination(TIMEOUT, UNIT))
                saveUncrawled(exec.getCancelledTasks());
        } finally {
            exec = null;
        }
    }

    protected abstract List<URL> processPage(URL url);

    private void saveUncrawled(List<Runnable> uncrawled) {
        for (Runnable task : uncrawled)
            urlsToCrawl.add(((CrawlTask) task).getPage());
    }

    private void submitCrawlTask(URL u) {
        exec.execute(new CrawlTask(u));
    }

    private class CrawlTask implements Runnable {
        private final URL url;

        CrawlTask(URL url) {
            this.url = url;
        }

        private int count = 1;

        boolean alreadyCrawled() {
            return seen.putIfAbsent(url, true) != null;
        }

        void markUncrawled() {
            seen.remove(url);
            System.out.printf("marking %s uncrawled%n", url);
        }

        public void run() {
            for (URL link : processPage(url)) {
                if (Thread.currentThread().isInterrupted())
                    return;
                submitCrawlTask(link);
            }
        }

        public URL getPage() {
            return url;
        }
    }
}
