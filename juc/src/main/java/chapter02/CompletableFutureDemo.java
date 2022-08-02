package chapter02;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * projectName:  juc
 * packageName: chapter02
 * date: 2022-07-25 21:47
 * author 邱依良
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new MyThread());

        Thread t1 = new Thread(futureTask,"t1");
        t1.start();

        System.out.println(futureTask.get());
    }
}


class MyThread implements Callable<String>{

    @Override
    public String call() throws Exception {
        System.out.println("come in call()");
        return "hello Callable";
    }
}
