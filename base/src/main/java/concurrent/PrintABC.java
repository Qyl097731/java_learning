package concurrent;

import org.junit.jupiter.api.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description
 * @date 2023/3/20 19:54
 * @author: qyl
 */
public class PrintABC {

    @Test
    public void testForSemaphore() {
        Semaphore semaphoreA = new Semaphore (1);
        Semaphore semaphoreB = new Semaphore (0);
        Semaphore semaphoreC = new Semaphore (0);

        new Thread (() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    semaphoreA.acquire ();
                    printf ('A');
                } catch (InterruptedException e) {
                    throw new RuntimeException (e);
                } finally {
                    semaphoreB.release ();
                }
            }
        }, "t1").start ();
        new Thread (() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    semaphoreB.acquire ();
                    printf ('B');
                } catch (InterruptedException e) {
                    throw new RuntimeException (e);
                } finally {
                    semaphoreC.release ();
                }
            }
        }, "t2").start ();
        new Thread (() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    semaphoreC.acquire ();
                    printf ('C');
                } catch (InterruptedException e) {
                    throw new RuntimeException (e);
                } finally {
                    semaphoreA.release ();
                }
            }
        }, "t3").start ();
    }

    volatile int token = 0;

    @Test
    public void testForVolatile() {
        new Thread (() -> {
            for (int i = 0; i < 20; i++) {
                while (token % 3 != 0) ;
                printf ('A');
                token++;
            }
        }, "t1").start ();
        new Thread (() -> {
            for (int i = 0; i < 20; i++) {
                while (token % 3 != 1) ;
                printf ('B');
                token++;
            }
        }, "t2").start ();
        new Thread (() -> {
            for (int i = 0; i < 20; i++) {
                while (token % 3 != 2) ;
                printf ('C');
                token++;
            }
        }, "t3").start ();
    }

    Lock lock = new ReentrantLock ();
    private int num = 0;

    class PrintTask implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 20; i++) {
                try {
                    lock.lock ();
                    char c = (char) ('A' + num % 3);
                    num++;
                    printf (c);
                } finally {
                    lock.unlock ();
                }
            }
        }
    }

    @Test
    public void testForLock() {
        ExecutorService threadPool = Executors.newFixedThreadPool (3);
        for (int i = 0; i < 3; i++) {
            threadPool.execute (new PrintTask ());
        }
    }

    @Test
    public void testForUnsafe() throws NoSuchFieldException, IllegalAccessException {
        Field field = Unsafe.class.getDeclaredField ("theUnsafe");
        field.setAccessible (true);
        Unsafe unsafe = (Unsafe) field.get (null);
        ExecutorService threadPool = Executors.newFixedThreadPool (3);
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            threadPool.execute (() -> {
                for (int j = 0; j < 20; j++) {
                    unsafe.loadFence ();
                    while (num % 3 != finalI);
                    char c = (char) ('A' + num % 3);
                    printf (c);
                    num++;
                }
            });
        }
    }


    private void printf(char c) {
        System.out.print (c);
    }
}
