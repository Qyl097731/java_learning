package util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;

public class ThreadPoolUtil {
    private static ExecutorService executor;
    private static final int poolsize = 2 * Runtime.getRuntime().availableProcessors() + 1;

    private ThreadPoolUtil() {
    }

    public static ExecutorService getExecutorService() {
        if (null == executor || executor.isShutdown() || executor.isTerminated()) {
            ThreadFactory namedThreadFactory = (new ThreadFactoryBuilder()).setNameFormat("nsec-pool-%d").build();
            executor = new ThreadPoolExecutor(poolsize, 200, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(1048576), namedThreadFactory, new AbortPolicy());
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
