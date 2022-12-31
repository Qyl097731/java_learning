package com.nju.concurrent.ch05;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @description
 * @date:2022/12/18 19:43
 * @author: qyl
 */
public class HiddenIterator {

    private final Set<Integer> set = new HashSet<> ( );

    public synchronized void add(Integer i) {
        set.add (i);
    }

    public synchronized void remove(Integer i) {
        set.remove (i);
    }

    public void addTenThings() {
        Random r = new Random ( );
        for (int i = 0; i < 10; i++) {
            add (r.nextInt ( ));
        }
        System.out.println ("added 10 elements to " + set);
    }
}
