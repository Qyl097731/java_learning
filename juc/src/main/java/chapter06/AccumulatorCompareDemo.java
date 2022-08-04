package chapter06;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author qyl
 * @program AccumulatorCompareDemo.java
 * @Description TODO
 * @createTime 2022-08-01 17:02
 */
class ClickNumber {
    int number = 0;

    public synchronized void clickBySync() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong();

    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();

    public void clickByLongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x + y, 0);

    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}

/**
 * @author nsec
 */
public class AccumulatorCompareDemo {
    public static final int _1M = 10000000;
    public static final int THREAD_NUMBER = 40;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long startTime;
        long endTime;

        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUMBER);


        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < _1M; j++) {
                        clickNumber.clickBySync();
                    }
                }finally {
                    countDownLatch1.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickBySync 耗费时间为\t"+(endTime-startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < _1M; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                }finally {
                    countDownLatch2.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByAtomicLong 耗费时间为\t"+(endTime-startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < _1M; j++) {
                        clickNumber.clickByLongAdder();
                    }
                }finally {
                    countDownLatch3.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByLongAdder 耗费时间为\t"+(endTime-startTime));

        startTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(()->{
                try {
                    for (int j = 0; j < _1M; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                }finally {
                    countDownLatch4.countDown();
                }
            },String.valueOf(i)).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("clickByLongAccumulator 耗费时间为\t"+(endTime-startTime));
    }

}
