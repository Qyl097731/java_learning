package com.nju.concurrent.ch07;

import net.jcip.annotations.GuardedBy;

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @description 使用TrackingExecutorService为后续执行来保存未完成的任务
 * @date:2022/12/23 15:32
 * @author: qyl
 */
public abstract class WebCrawler {
    private volatile TrackingExecutor exec;
    @GuardedBy("this")
    private final Set<URL> urlsToCrawl = new HashSet<> ( );
    private static final long TIMEOUT = 1;
    private static final TimeUnit UNIT = TimeUnit.SECONDS;


    public synchronized void start() {
        exec = new TrackingExecutor (Executors.newCachedThreadPool ( ));
        for (URL url : urlsToCrawl) {
            submitCrawlTask (url);
        }
        urlsToCrawl.clear ( );
    }

    private void submitCrawlTask(URL url) {
        exec.execute (new CrawlTask(url));
    }

    public synchronized void stop() throws InterruptedException {
        try {
            saveUncrawled (exec.shutdownNow ( ));
            if (exec.awaitTermination (TIMEOUT, UNIT)) {
                saveUncrawled (exec.getCancelledTasks ( ));
            }
        } finally {
            exec = null;
        }
    }

    private void saveUncrawled(List<Runnable> uncrwaled) {
        for (Runnable task : uncrwaled) {
            urlsToCrawl.add (((CrawlTask)task).getPage());
        }
    }

    private class CrawlTask implements Runnable{
        private final URL url;

        private CrawlTask(URL url) {
            this.url = url;
        }


        @Override
        public void run() {
            for (URL link : processPage (url)) {
                if (Thread.currentThread ().isInterrupted ())
                    return;
                submitCrawlTask (link);
            }
        }

        private List<URL> processPage(URL url) {
            return Collections.singletonList (url);
        }

        public URL getPage() {
            return url;
        }
    }
}
