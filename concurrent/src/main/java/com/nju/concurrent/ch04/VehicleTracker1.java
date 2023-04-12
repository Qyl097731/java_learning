package com.nju.concurrent.ch04;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @description Java监视器模式 封装所有状态可变的类，并通过自身进行保护, 效率低下
 * @date:2022/12/17 18:06
 * @author: qyl
 */
@ThreadSafe
public class VehicleTracker1 {
    /**
     * 通过自己实例对象进行保护，方法之间串行执行
     */
    @GuardedBy("this")
    private final Map<String, MutablePoint> locations;

    public VehicleTracker1(Map<String, MutablePoint> locations) {
        this.locations = deepCopy (locations);
    }

    public synchronized Map<String, MutablePoint> getLocations() {
        return deepCopy (locations);
    }

    public synchronized MutablePoint getLocation(String id) {
        MutablePoint location = locations.get (id);
        if (location == null) {
            return null;
        }
        return new MutablePoint (location.x, location.y);
    }

    public synchronized void setLocation(String id, int x, int y) {
        MutablePoint location = locations.get (id);
        if (location == null) {
            throw new IllegalArgumentException ("no such id " + id);
        }
        location.x = x;
        location.y = y;
    }

    private Map<String, MutablePoint> deepCopy(Map<String, MutablePoint> locations) {
        Map<String, MutablePoint> result = new HashMap<> (locations.size ( ));
        for (Map.Entry<String, MutablePoint> entry : locations.entrySet ( )) {
            MutablePoint point = entry.getValue ( );
            result.put (entry.getKey ( ), new MutablePoint (point.x, point.y));
        }
        return Collections.unmodifiableMap (result);
    }
}

@NotThreadSafe
class MutablePoint {
    public int x, y;

    public MutablePoint() {
        this (0, 0);
    }

    public MutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
