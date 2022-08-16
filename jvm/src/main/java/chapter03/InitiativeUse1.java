package chapter03;

import org.junit.Test;

import java.util.Random;

/**
 * @description
 * @date:2022/8/16 21:53
 * @author: qyl
 */
public class InitiativeUse1 {

    @Test
    public void test1(){
        System.out.println(User.num);
    }

    @Test
    public void test2(){
        System.out.println(User.fnum);
    }

    @Test
    public void test3(){
        System.out.println(CompareA.NUM1);
        System.out.println(CompareA.NUM2);

    }
}
class User{
    static {
        System.out.println("user 初始化");
    }
    public static int num = 1;
    public static final int fnum = 2;
}

interface CompareA{
    public static final int NUM2 = new Random().nextInt(19);
    public static final int NUM1 = 1;
    public static final Thread t = new Thread(){{System.out.println("hhh");}};
}
