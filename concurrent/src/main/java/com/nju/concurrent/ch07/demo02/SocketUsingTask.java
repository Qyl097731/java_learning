package com.nju.concurrent.ch07.demo02;

import net.jcip.annotations.GuardedBy;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * @description
 * @date:2022/12/22 16:56
 * @author: qyl
 */
public abstract class SocketUsingTask<T> implements CancellableTask<T> {
    @GuardedBy("this")
    private Socket socket;

    protected synchronized void setSocket(Socket s) {
        socket = s;
    }

    @Override
    public synchronized void cancel() {
        try {
            if (socket != null) {
                socket.close ( );
            }
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

    @Override
    public RunnableFuture<T> newTask() {
        return new FutureTask<T> (this) {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUsingTask.this.cancel ( );
                } finally {
                    return super.cancel (mayInterruptIfRunning);
                }
            }
        };
    }
}
