package com.nju.concurrent.ch07;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description UncaughtExceptionHandler 将异常 写入日志
 * @date:2022/12/23 17:15
 * @author: qyl
 */
public class UEHLogger implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger ( );
        logger.log (Level.SEVERE,"Thread terminated with exception: " + t.getName ());
    }
}
