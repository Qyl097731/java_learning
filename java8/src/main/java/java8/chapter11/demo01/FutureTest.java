package java8.chapter11.demo01;

import java.util.concurrent.*;

/**
 * @date:2022/11/1 9:53
 * @author: qyl
 */
public class FutureTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        ExecutorService executor = Executors.newCachedThreadPool();
        // 提交异步任务
        Future<Double> future = executor.submit(FutureTest::doSomeLongComputation);
        // 异步操作的同时做其他事情
        doSomethingElse();
        // 等待异步操作的结果，一秒钟没响应就退出
        Double result = future.get(1, TimeUnit.SECONDS);
        System.out.println("异步结束 " + result);
        executor.shutdown();
    }

    private static void doSomethingElse() {
        System.out.println("ha ha ha~");
    }

    private static Double doSomeLongComputation() {
        return 1.0;
    }
}
