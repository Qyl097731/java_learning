package chapter15;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program MapTest.java
 * @Description 缓存泄露
 * @createTime 2022-08-19 13:23
 */
public class MapTest {
    static Map wMap = new WeakHashMap<>();
    static Map map = new HashMap();

    public static void main(String[] args) {
        init();
        testWeakHashMap();
        testHashMap();
    }

    public static void init(){
        String ref1 = new String("object1");
        String ref2 = new String("object2");
        String ref3 = new String("object3");
        String ref4 = new String("object4");
        wMap.put(ref1,"cacheObj1");
        wMap.put(ref2,"cacheObj2");
        map.put(ref3,"cacheObj3");
        map.put(ref4,"cacheObj4");
        System.out.println("String 引用 ref1、2、3、4 消失");
    }

    public static void testWeakHashMap(){
        System.out.println("WeakHashMap GC之前");
        for (Object o : wMap.entrySet()){
            System.out.println(o);
        }
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("WeakHashMap GC之后");
        for (Object o : wMap.entrySet()){
            System.out.println(o);
        }
    }

    public static void testHashMap(){
        System.out.println("Map GC之前");
        for (Object o : map.entrySet()){
            System.out.println(o);
        }
        System.gc();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Map GC之后");
        for (Object o : map.entrySet()){
            System.out.println(o);
        }
    }

}
