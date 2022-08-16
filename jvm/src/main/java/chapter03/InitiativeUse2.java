package chapter03;

import jdk.nashorn.tools.Shell;
import org.junit.Test;

/**
 * @description
 * @date:2022/8/16 22:13
 * @author: qyl
 */
public class InitiativeUse2 {
    static {
        System.out.println("InitiativeUse2");
    }
    @Test
    public void test1(){
        try{
            Class clazz = Class.forName("chapter03.Order");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2(){
        System.out.println(Son.num);
    }

    @Test
    public void test3() {
        System.out.println(Son.num);
    }

    public static void main(String[] args) {
        System.out.println("main");
    }
}
class Father{
    static {
        System.out.println("father 初始化");
    }
}


class Son extends Father implements CompareB{
    static {
        System.out.println("son 初始化");
    }
    public static int num = 1;
}
interface CompareB{
    public static final Thread t = new Thread(){{
        System.out.println("hello");
    }};

    default void testDefault(){
        System.out.println(1111);
    }
    public static final String s = new String("s");
}
