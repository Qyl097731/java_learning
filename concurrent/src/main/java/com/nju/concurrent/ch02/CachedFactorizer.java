package com.nju.concurrent.ch02;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 * @description 保证尽可能少得请求锁、释放锁带来的开销的同时又能防止长时间的操作导致阻塞过多的服务
 * @date:2022/12/14 20:22
 * @author: qyl
 */
@ThreadSafe
public class CachedFactorizer {
    @GuardedBy("this")
    private BigInteger lastNumber;
    @GuardedBy("this")
    private long hits;
    @GuardedBy("this")
    private long cacheHits;

    public void service(ServletRequest req, ServletResponse resp) throws InterruptedException {
        BigInteger i = new BigInteger (String.valueOf (req.getAttribute ("num")));
        synchronized (this) {
            ++hits;
            if (i.equals (lastNumber)) {
                ++cacheHits;
            }
        }

        TimeUnit.SECONDS.sleep (10);

        synchronized (this) {
            System.out.println ("hits = " + hits);
        }
    }

    private synchronized long getHits() {
        return hits;
    }

    private synchronized double getCacheHitRatio() {
        return (double) cacheHits / (double) hits;
    }

}
