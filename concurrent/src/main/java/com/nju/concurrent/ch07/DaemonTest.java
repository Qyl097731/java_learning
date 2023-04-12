package com.nju.concurrent.ch07;

import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @description 守护线程、父子线程的测试
 * @date:2022/12/23 19:44
 * @author: qyl
 */
public class DaemonTest {
    static Logger logger = LoggerFactory.getLogger (DaemonTest.class);

    private static void testForDaemonBetweenInherit() {
        Thread thread = Thread.currentThread ( );
        // 测试一下默认的线程类型
        boolean daemon = thread.isDaemon ( );
        Assertions.assertFalse (daemon);

        // 测试一下子线程是否和父线程的线程类型一致
        Thread t = new Thread (() ->
                Assertions.assertFalse (Thread.currentThread ( ).isDaemon ( ))
        );
        t.start ( );
    }

    private static void testForDaemonWhenExit() throws InterruptedException {
        Thread thread = Thread.currentThread ( );
        // 测试一下默认的线程类型
        boolean daemon = thread.isDaemon ( );
        Assertions.assertFalse (daemon);

        for (int i = 0; i < 5; i++) {
            Thread t = new Thread (() -> {
                // 测试一下子线程类型
                Assertions.assertTrue (Thread.currentThread ( ).isDaemon ( ));
                // 查看一下是否会执行finally
                try {
                    while (true) {
                        TimeUnit.SECONDS.sleep (3);
                    }
                } catch (InterruptedException e) {
                } finally {
                    logger.info (() -> "程序结束，运行daemon的finally");
                }
            });
            t.setDaemon (true);
            logger.info (() -> "run...");
            t.start ( );
        }
        logger.info (() -> "启动全部守护线程");
        TimeUnit.SECONDS.sleep (5);
    }

    private static void testForNormalWhenExit() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread (() -> {
                // 测试一下子线程类型
                Assertions.assertFalse (Thread.currentThread ( ).isDaemon ( ));
                // 查看一下是否会执行finally
                try {
                    TimeUnit.SECONDS.sleep (10);
                } catch (InterruptedException e) {
                } finally {
                    logger.info (() -> "程序结束，运行finally");
                }
            });
            logger.info (() -> "run...");
            t.start ( );
        }
        logger.info (() -> "启动全部守护线程");
        TimeUnit.SECONDS.sleep (3);
        logger.info (() -> "主线程结束");
    }

    public static void main(String[] args) throws InterruptedException {
        testForDaemonBetweenInherit ();
        testForDaemonWhenExit ();
        testForNormalWhenExit ();
    }
}
