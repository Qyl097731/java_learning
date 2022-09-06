package chapter9;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

/**
 * @description
 * @date:2022/9/6 21:37
 * @author: qyl
 */
public class ReflectiveTest {
    public static void main(String[] args) {
        Class<? extends Set<String>> clazz = null;
        try{
            clazz = (Class<? extends Set<String>>)Class.forName(args[0]);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Constructor<? extends Set<String>> cons = null;
        try {
            cons = clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        Set<String> s = null;
        try{
            s = cons.newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        s.addAll(Arrays.asList(args).subList(1,args.length));
        System.out.println(s);
    }
}
