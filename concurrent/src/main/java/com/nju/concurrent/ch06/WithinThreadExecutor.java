package com.nju.concurrent.ch06;

import java.util.concurrent.Executor;

/**
 * @description 同步执行
 * @date:2022/12/21 8:54
 * @author: qyl
 */
public class WithinThreadExecutor implements Executor {
    @Override
    public void execute(Runnable command) {
        command.run ();
    }
}
