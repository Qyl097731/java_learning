package com.nju.concurrent.ch11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @description 通过分拆的锁重构ServerStatus
 * @date:2022/12/31 22:40
 * @author: qyl
 */
@ThreadSafe
public class ServerStatus2 {
    @GuardedBy ("user") public final Set<String> users;
    @GuardedBy ("queries") public final Set<String> queries;

    public ServerStatus2() {
        this.users = new HashSet<> ();
        this.queries = new HashSet<> ();
    }

    public void addUser(String u){
        synchronized (users){
            users.add (u);
        }
    }

    public void addQuery(String q){
        synchronized (queries){
            queries.add (q);
        }
    }
    //.. 继续分拆进行类似的重构
}
