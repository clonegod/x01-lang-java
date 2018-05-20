package juc.executor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import conc.util.LaunderThrowable;

/**
 * Renderer
 * <p/>
 * Using CompletionService to render page elements as they become available
 *
 * @author Brian Goetz and Tim Peierls
 */
public abstract class RendererPageByExecutorCompletionService {
    private final ExecutorService executor; 

    RendererPageByExecutorCompletionService(ExecutorService executor) {
        this.executor = executor;
    }

    void renderPage(CharSequence source) {
        final List<ImageInfo> info = scanForImageInfo(source);
        
        /** 技巧：使用系统共享的executor，当需要使用CompletionFirst特性时，再将executor组合进去，这样可以实现executor的复用！！而且对外都是统一的executor */
        CompletionService<ImageData> completionService =
                new ExecutorCompletionService<ImageData>(executor);
        for (final ImageInfo imageInfo : info)
            completionService.submit(new Callable<ImageData>() {
                public ImageData call() {
                    return imageInfo.downloadImage();
                }
            });

        renderText(source);

        try {
            for (int t = 0, n = info.size(); t < n; t++) {
            	/** 只要有任一图像下载完成，就立即显示到页面上，而不用等待所有任务都执行完成 */
                Future<ImageData> f = completionService.take(); // 阻塞式获取任务结果
                ImageData imageData = f.get(); 
                renderImage(imageData);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw LaunderThrowable.launderThrowable(e.getCause());
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);

}
