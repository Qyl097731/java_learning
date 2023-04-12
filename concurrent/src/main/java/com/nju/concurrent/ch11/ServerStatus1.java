package com.nju.concurrent.ch11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 * @description 应当分拆锁的候选程序
 * @date:2022/12/31 22:36
 * @author: qyl
 */
@ThreadSafe
public class ServerStatus1 {
    @GuardedBy ("this") public final Set<String> users;
    @GuardedBy ("this") public final Set<String> queries;

    public ServerStatus1() {
        this.users = new HashSet<> ();
        this.queries = new HashSet<> ();
    }

    public synchronized void addUser(String u){users.add (u);}
    public synchronized void addQuery(String q){queries.add (q);}
    public synchronized void removeUser(String u){users.remove (u);}
    public synchronized void removeQuery(String q){
        queries.remove (q);
    }
}
