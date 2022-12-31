package com.nju.concurrent.ch09;

import java.util.concurrent.*;

/**
 * @description 通过done触发一个已经完成的任务，我们能够构建BackgroundTask，提供一个在事件线程中调用的 onCompletion函数 ；
 * compute函数可以调用setProgress来显示进度，引起事件线程调用onProgress更新显示进度
 * @date:2022/12/26 21:42
 * @author: qyl
 */
public abstract class BackgroundTask <V> implements Runnable, Future<V> {
    private final FutureTask<V> computation = new Computation();

    private class Computation extends FutureTask<V>{

        public Computation() {
            super (BackgroundTask.this::compute);
        }

        protected final void done(){
            GuiExecutor.instange ().execute (new Runnable () {
                @Override
                public void run() {
                    V value = null;
                    boolean cancelled = false;
                    Throwable thrown = null;
                    try {
                        value = get ();
                    } catch (ExecutionException e) {
                        thrown = e.getCause ();
                    } catch (InterruptedException e) {
                    } catch (CancellationException e){
                        cancelled = true;
                    }finally {
                        onCompletion (value,thrown,cancelled);
                    }
                }
            });
        }
    }

    // 在后台线程中调用
    protected abstract V compute() throws Exception;

    // 在事件线程中调用
    protected void onCompletion(V result,Throwable exception,boolean cancelled){}

    protected void onProgress(int current,int max){}

}
