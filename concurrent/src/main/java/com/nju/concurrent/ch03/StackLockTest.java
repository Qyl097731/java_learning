package com.nju.concurrent.ch03;

import com.nju.concurrent.entity.User;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @description 把所有的对象都限制在了方法栈中，一旦离开就已经销毁了！
 * @date:2022/12/16 21:24
 * @author: qyl
 */
public class StackLockTest {
    @Test
    public void test() {
        loadTheArk (List.of (new User ("tomy",1),new User ("jack",60),new User ("andy",40)));
    }

    public int loadTheArk(Collection<User> candidates) {
        SortedSet<User> users;
        int numPairs = 0;
        User candidate = null;

        // 所有的对象都限制在该方法栈中，不要让他们溢出，即不传递出去
        users = new TreeSet<> (candidates);
        for (User u : users) {
            if (candidate == null || candidate.age <= 55){
                candidate = u;
            }else {
                ++numPairs;
                candidate = null;
            }
        }
        return numPairs;
    }
}
