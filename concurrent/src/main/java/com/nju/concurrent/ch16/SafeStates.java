package com.nju.concurrent.ch16;

import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @description 不可变对象的初始化安全性
 * @date:2023/1/13 19:56
 * @author: qyl
 */
@ThreadSafe
public class SafeStates {
    private final Map<String,String> states;

    public SafeStates() {
        this.states = new HashMap<> ();
        states.put ("alaska","AK");
        states.put ("aska","AS");
    }

    public String getAbbreviation(String s){
        return states.get (s);
    }

}
