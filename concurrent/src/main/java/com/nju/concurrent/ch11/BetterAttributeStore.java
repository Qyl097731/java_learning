package com.nju.concurrent.ch11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @description 通过减小锁的粒度，来减少持续时间
 * @date:2022/12/31 22:24
 * @author: qyl
 */
@ThreadSafe
public class BetterAttributeStore {
    @GuardedBy("this")
    private final Map<String, String> attributes = new HashMap<> ();

    public boolean userLocationMatches(String name, String regexp) {
        String key = "users." + name + ".location";
        String location;
        synchronized (this) {
            location = attributes.get (key);
        }
        if (location == null) {
            return false;
        } else {
            return Pattern.matches (regexp, location);
        }
    }
}
