package com.nju.concurrent.ch12;

import org.junit.jupiter.api.Assertions;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @description BoundedBuffer的生产者-消费者测试程序
 * @date:2023/1/2 20:01
 * @author: qyl
 */
public class PutTakeTest {
    private static final ExecutorService pool = Executors.newCachedThreadPool ();
    private final AtomicInteger putSum = new AtomicInteger (0);
    private final AtomicInteger takeSum = new AtomicInteger (0);
    private final CyclicBarrier barrier;
    private final BoundedBuffer<Integer> bb;
    private final int nTrials, nPairs;
    private final BarrierTimer timer;

    public PutTakeTest(int capacity, int npairs, int ntrials) {
        this.bb = new BoundedBuffer<> (capacity);
        this.nPairs = npairs;
        this.nTrials = ntrials;
        this.timer = new BarrierTimer ();
        // 关卡冲破就执行这个计时动作
        this.barrier = new CyclicBarrier (npairs * 2 + 1,timer);
    }

    static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

    void test() {
        try {
            timer.clear ();
            for (int i = 0; i < nPairs; i++) {
                pool.execute (new Producer ());
                pool.execute (new Consumer ());
            }
            barrier.await (); // 等待所有线程做好准备 第一次冲关之后 关卡恢复
            barrier.await (); // 等待所有线程最终完成 第二次冲关等待
            long nsPerItem = timer.getTime () / (nPairs * (long) nTrials);
            System.out.print ("Throughput : " + nsPerItem + " ns / item");
            assertEquals (putSum.get (), takeSum.get ());
        } catch (BrokenBarrierException | InterruptedException e) {
            throw new RuntimeException (e);
        }
    }

    static void timerPutTakeTest() throws InterruptedException {
        int tpt = 100000;   // 线程尝试的次数
        for (int cap = 1; cap <= 1000; cap *= 10) {
            System.out.println ("Capacity: " + cap);
            for (int pairs = 1; pairs <= 128; pairs *= 2) {
                PutTakeTest t = new PutTakeTest (cap, pairs, tpt);
                System.out.print ("Pairs " + pairs + "\t");
                t.test ();
                System.out.print ("\t");
                Thread.sleep (1000);
                t.test ();
                System.out.println ();
                Thread.sleep (1000);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
//        new PutTakeTest (10, 10, 100000).test ();
        timerPutTakeTest ();
        pool.shutdown ();
    }

    class Producer implements Runnable {
        @Override
        public void run() {
            try {
                int seed = (this.hashCode () ^ (int) System.nanoTime ());
                int sum = 0;
                barrier.await ();
                for (int i = nTrials; i > 0; i--) {
                    bb.put (seed);
                    sum += seed;
                    seed = xorShift (seed);
                }
                putSum.getAndAdd (sum);
                barrier.await ();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException (e);
            }
        }
    }

    class Consumer implements Runnable {
        @Override
        public void run() {
            try {
                barrier.await ();
                int sum = 0;
                for (int i = nTrials; i > 0; i--) {
                    sum += bb.take ();
                }
                takeSum.getAndAdd (sum);
                barrier.await ();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException (e);
            }
        }
    }
}


