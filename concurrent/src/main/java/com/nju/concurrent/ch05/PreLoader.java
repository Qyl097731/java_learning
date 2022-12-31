package com.nju.concurrent.ch05;

import com.nju.concurrent.entity.User;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @description 预先计算之后需要的数据 即先启动 start ，之后需要该数据了 在进行get即可。
 * @date:2022/12/19 19:01
 * @author: qyl
 */
public class PreLoader {
    static User user;

    static {
        user = new User ("qyl", 24);
    }

    private final FutureTask<User> future = new FutureTask<User> (this::loadUserInfo);

    private final Thread thread = new Thread (future);

    public void start() {
        thread.start ( );
    }

    public User get() throws ExecutionException, InterruptedException {
        return future.get ();
    }

    /**
     * 模拟从数据库加载数据
     * @return
     */
    private User loadUserInfo() {
        try {
            TimeUnit.SECONDS.sleep (2);
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
        return user;
    }
}
