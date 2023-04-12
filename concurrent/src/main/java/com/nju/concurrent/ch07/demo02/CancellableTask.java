package com.nju.concurrent.ch07.demo02;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

/**
 * @description 用newTaskFor封装任务中非标准取消
 * @date:2022/12/22 16:50
 * @author: qyl
 */
public interface CancellableTask<T> extends Callable<T> {
    void cancel();
    RunnableFuture<T> newTask();
}
