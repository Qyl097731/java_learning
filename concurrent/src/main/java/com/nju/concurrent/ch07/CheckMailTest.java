package com.nju.concurrent.ch07;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @description 使用私有Executor，将它的寿命限定于一次方法调用中
 * @date:2022/12/23 11:14
 * @author: qyl
 */
public class CheckMailTest {
    boolean checkMail(Set<String> hosts, long timeout, TimeUnit unit) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool ( );
        final AtomicBoolean hasNewMail = new AtomicBoolean (false);
        try {
            for (String host : hosts) {
                exec.execute (() -> {
                    if (checkMail (host)) {
                        hasNewMail.set (true);
                    }
                });
            }
        } finally {
            exec.shutdown ( );
            exec.awaitTermination (timeout, unit);
        }
        return hasNewMail.get ( );
    }

    private boolean checkMail(String host) {
        return false;
    }
}
