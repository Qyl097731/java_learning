package com.nju.concurrent.ch06;

import java.util.concurrent.Executor;

/**
 * @date:2022/12/21 8:53
 * @author: qyl
 */
public class ThreadPerTaskExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        new Thread (command).run ();
    }
}
