package chapter04;

import java.util.Date;

/**
 * @description
 * @date:2022/8/15 21:36
 * @author: qyl
 */
public class MethodInvokeReturnTest {
    // invokespecial
    public void invoke1() {
        // 实例化方法
        Date date = new Date();
        // 父类方法
        super.toString();
        // 私有方法
        methodPrivate();
    }

    // invokestatic
    public void invoke2() {
        methodStatic();
    }

    // invokeinterface
    public void invoke3() {
        Thread t1 = new Thread();
        ((Runnable) t1).run();

        Comparable<Integer> com = null;
        com.compareTo(123);
    }

    // invokevirtual
    public void invoke4(){
        System.out.println("hello");

        Thread t1 = new Thread();
        t1.run();
    }

    // 方法返回命令
    public int returnInt(){
        int i = 500;
        return i;
    }

    public static void methodStatic() {
    }

    private void methodPrivate() {
    }
}
