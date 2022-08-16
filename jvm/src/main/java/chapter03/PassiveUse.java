package chapter03;

import org.junit.Test;

/**
 * @description
 * @date:2022/8/16 22:38
 * @author: qyl
 */
public class PassiveUse {
    @Test
    public void test(){
        System.out.println(Child.num);
    }

    @Test
    public void test2() {
        Parent[] parents = new Parent[10];
    }

    @Test
    public void test3() throws ClassNotFoundException {
        ClassLoader.getSystemClassLoader().loadClass("chapter03.Order");
    }
}
class Parent{
    static {
        System.out.println("Parent的初始化过程");
    }
    public static int num = 1;
}
class Child extends Parent{
    static {
        System.out.println("Child的初始化过程");
    }
}
