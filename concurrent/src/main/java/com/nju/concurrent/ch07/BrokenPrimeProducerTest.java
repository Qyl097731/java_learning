package com.nju.concurrent.ch07;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description 生产者的速度超过了消费者速度，这个时候put阻塞，导致即使已经cancel了，都无法进行判断
 * @date:2022/12/21 16:26
 * @author: qyl
 */
public class BrokenPrimeProducerTest {
    @Test
    public void test() throws InterruptedException {
        consumePrimes ( );
    }


    /**
     * 消费者
     */
    void consumePrimes() throws InterruptedException {
        BlockingQueue<BigInteger> primes = new LinkedBlockingQueue<> ( );
//        BrokenPrimeProducer producer = new BrokenPrimeProducer (primes);
        PrimeProducer producer = new PrimeProducer (primes);
        producer.start ( );

        TimeUnit.SECONDS.sleep (10);
        producer.cancel ( );
    }

    /**
     * 生产者
     */
    class BrokenPrimeProducer extends Thread {
        private final BlockingQueue<BigInteger> queue;
        private volatile boolean cancelled = false;

        public BrokenPrimeProducer(BlockingQueue<BigInteger> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                BigInteger p = BigInteger.ONE;
                while (!cancelled) {
                    queue.put (p = p.nextProbablePrime ( ));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException (e);
            }
        }

        public void cancel() {
            cancelled = true;
        }

    }

    /**
     * 运用中断进行取消
     */
    class PrimeProducer extends Thread {
        private final BlockingQueue<BigInteger> queue;

        PrimeProducer(BlockingQueue<BigInteger> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                BigInteger p = BigInteger.ONE;
                // 通过轮询检查，避免多进行一次耗时获取下一个素数
                while (!Thread.currentThread ( ).isInterrupted ( )) {
                    queue.put (p = p.nextProbablePrime ( ));
                }
            } catch (InterruptedException e) {
                // 捕获在阻塞过程中就被中断
            }
        }

        public void cancel() {
            interrupt ( );
        }
    }
}


