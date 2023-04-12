package com.nju.concurrent.ch13;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @description 用读写锁包装的Map
 * @date:2023/1/3 16:57
 * @author: qyl
 */
public class ReadWriteMap<K,V> {
    private final Map<K,V> map;
    private final ReadWriteLock lock = new ReentrantReadWriteLock ();
    private final Lock r = lock.readLock ();
    private final Lock w = lock.writeLock ();

    public ReadWriteMap(Map<K,V> map){this.map = map;}

    public V put(K key,V value){
        w.lock ();
        try {
            return map.put (key,value);
        }finally {
            w.unlock ();
        }
    }

    public V remove(K key){
        w.lock ();
        try {
            return map.remove (key);
        }finally {
            w.unlock ();
        }
    }

    public void clear(){
        w.lock ();
        try {
            map.clear ();
        }finally {
            w.unlock ();
        }
    }

    public void putAll(Map<K,V> map){
        w.lock ();
        try {
            map.putAll (new HashMap<> (map));
        }finally {
            w.unlock ();
        }
    }

    public V get(K key){
        r.lock ();
        try {
            return map.get (key);
        }finally {
            r.unlock ();
        }
    }

}
