package com.nju.concurrent.ch09;

import java.util.List;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @description 基于SwingUtilities之上的Executor
 * @date:2022/12/26 18:45
 * @author: qyl
 */
public class GuiExecutor extends AbstractExecutorService {
    // Singleton 包含一个私有的构造函数和一个公共的工厂
    private static final GuiExecutor instance = new GuiExecutor ();

    private GuiExecutor() {
    }

    public static GuiExecutor instange() {
        return instance;
    }

    @Override
    public void shutdown() {
        instance.shutdown ();
    }

    @Override
    public List<Runnable> shutdownNow() {
        return instance.shutdownNow ();
    }

    @Override
    public boolean isShutdown() {
        return instance.isShutdown ();
    }

    @Override
    public boolean isTerminated() {
        return instance.isTerminated ();
    }

    @Override
    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        return instance.awaitTermination (timeout,unit);
    }

    @Override
    public void execute(Runnable command) {
        if (SwingUtilities.isEventDispatchThread ()){
            command.run ();
        }else {
            SwingUtilities.invokeLater (command);
        }
    }
}
