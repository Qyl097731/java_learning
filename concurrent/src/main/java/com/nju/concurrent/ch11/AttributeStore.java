package com.nju.concurrent.ch11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @description 持有锁超过了必要时间
 * @date:2022/12/31 22:22
 * @author: qyl
 */
@ThreadSafe
public class AttributeStore {
    @GuardedBy ("this")
    private final Map<String,String> attributes = new HashMap<> ();

    public synchronized boolean userLocationMatches(String name,String regexp){
        String key = "users." + name + ".location";
        String location = attributes.get (key);
        if (location == null){
            return false;
        }else {
            return Pattern.matches (regexp,location);
        }
    }
}
