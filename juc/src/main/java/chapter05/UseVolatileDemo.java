package chapter05;

/**
 * @author qyl
 * @program UseVolatileDemo.java
 * @Description TODO
 * @createTime 2022-08-01 14:05
 */
public class UseVolatileDemo {
    private volatile static boolean flag = true;

    public static void main(String[] args) {
        new Thread(()->{
            while(flag){

            }

        },"t1").start();

        new Thread(()->{
            flag = false;
        },"t2").start();
    }
}
