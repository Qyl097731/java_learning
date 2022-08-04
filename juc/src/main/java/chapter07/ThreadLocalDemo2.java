package chapter07;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author qyl
 * @program ThreadLocalDemo2.java
 * @Description TODO
 * @createTime 2022-08-02 11:08
 */
public class ThreadLocalDemo2 {
    public static final Integer N_CPU = Runtime.getRuntime().availableProcessors();

    public static ExecutorService getThreadPool() {
        return new ThreadPoolExecutor(N_CPU, N_CPU * 2 + 1, 0L, TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build());
    }

    public static void main(String[] args) {
        MyData myData = new MyData();
        ExecutorService threadPool = getThreadPool();
        try {
            for (int i = 0; i < 100; i++) {

                threadPool.submit(() -> {
                    try {
                        Integer beforeInt = myData.threadLocal.get();
                        myData.add();
                        Integer afterInt = myData.threadLocal.get();
                        System.out.println(Thread.currentThread().getName() + "\t beforeInt " + beforeInt + "\t afterInt " + afterInt);
                    }finally{
                        myData.threadLocal.remove();
                    }
                });
            }
        } catch (Exception e) {

        } finally {
            threadPool.shutdown();
        }
    }
}

class MyData {
    ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial(() -> 0);

    public void add() {
        threadLocal.set(1 + threadLocal.get());
    }
}
