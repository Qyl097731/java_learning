package chapter05;

import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program VolatileSeeDemo
 * @Description TODO
 * @createTime 2022-08-01 13:18
 */
public class VolatileSeeDemo {
    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(()->{
            System.out.println(Thread.currentThread().getName() +"\t ---- come in");
            while (flag){

            }
            System.out.println(Thread.currentThread().getName() +"\t ---- flag 被修改了");
        },"t1").start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = false;
        System.out.println(Thread.currentThread().getName() +"\t ---- flag 修改完成");

    }
}
