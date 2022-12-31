package com.nju.concurrent.ch04;

import net.jcip.annotations.Immutable;
import net.jcip.annotations.ThreadSafe;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 委托线程安全 通过ConcurrentHashMap 来保证集合的同步， 通过point的不可变，使得能够安全发布位置状态
 * @date:2022/12/17 18:19
 * @author: qyl
 */
public class DelegatingSafeTest {
}

@ThreadSafe
class VehicleTracker2 {
    private final Map<String, ImmutablePoint> locations;
    private final Map<String, ImmutablePoint> unmodifiableMap;

    public VehicleTracker2(Map<String, ImmutablePoint> locations) {
        this.locations = new ConcurrentHashMap<> (locations);
        unmodifiableMap = Collections.unmodifiableMap (this.locations);
    }

    public Map<String, ImmutablePoint> getLocations() {
        return unmodifiableMap;
    }

    public ImmutablePoint getLocation(String id) {
        return locations.get (id);
    }

    public void setLocation(String id, int x, int y) {
        if (locations.replace (id, new ImmutablePoint (x, y)) == null) {
            throw new IllegalArgumentException ("invalid vehicle name " + id);
        }
    }
}

@Immutable
class ImmutablePoint {
    public final int x, y;

    public ImmutablePoint() {
        this (0, 0);
    }

    ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
