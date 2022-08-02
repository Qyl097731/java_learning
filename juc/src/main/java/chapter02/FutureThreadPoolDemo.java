package chapter02;

import javax.xml.transform.Source;
import java.util.concurrent.*;

/**
 * projectName:  juc
 * packageName: chapter02
 * date: 2022-07-25 21:57
 * author 邱依良
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();
        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task1";
        });
        threadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<String>(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task2";
        });
        threadPool.submit(futureTask2);

        FutureTask<String> futureTask3 = new FutureTask<String>(() -> {
            try {
                TimeUnit.MICROSECONDS.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "task3";
        });
        threadPool.submit(futureTask3);

        futureTask1.get();
        futureTask2.get();
        futureTask3.get();


        long endTime = System.currentTimeMillis();

        System.out.println("-----costTime : " + (endTime - startTime) + "ms");
        System.out.println(Thread.currentThread().getName() + "\t ------- end");

        threadPool.shutdown();

        m1();

    }

    public static void m1() {
        long startTime = System.currentTimeMillis();

        try {
            TimeUnit.MICROSECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            TimeUnit.MICROSECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            TimeUnit.MICROSECONDS.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();

        System.out.println("-----costTime : " + (endTime - startTime) + "ms");

        System.out.println(Thread.currentThread().getName() + "\t ------- end");
    }
}
