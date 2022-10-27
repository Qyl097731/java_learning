package chapter09;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

/**
 * @author asus
 */
public class ThreadPoolUtil {
    private static ExecutorService executor;
    private static final int POOL_SIZE = 2 * Runtime.getRuntime().availableProcessors() + 1;

    private ThreadPoolUtil() {
    }

    public static ExecutorService getExecutorService() {
        if (null == executor || executor.isShutdown() || executor.isTerminated()) {
            ThreadFactory namedThreadFactory = (new ThreadFactoryBuilder()).setNameFormat("nju-pool-%d").build();
            executor = new ThreadPoolExecutor(POOL_SIZE, 200, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(1048576), namedThreadFactory, new AbortPolicy());
        }

        return executor;
    }

    public static void closeThreadPool() {
        executor.shutdown();
    }

    static {
        getExecutorService();
    }
}
