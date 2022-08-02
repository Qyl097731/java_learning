package chapter04;

import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/7/31 15:21
 * @author: qyl
 */
public class InterruptDemo2 {
    public static void main(String[] args) {
        //实例方法interrupt)只是设置中断状态不会停止线程
          Thread t1 = new Thread(()->{
              for (int i = 1;i <= 300 ;i++){
                  System.out.println("============" + i);
              }
              System.out.println("t1线程调用interrupt()后的中断标识02：" + Thread.currentThread().isInterrupted());
          },"t1");
          t1.start();

        System.out.println("t1线程默认的中断标识" + t1.isInterrupted());

        try{TimeUnit.MICROSECONDS.sleep(2);}catch (InterruptedException e){e.printStackTrace();}

        t1.interrupt();

        System.out.println("t1线程调用interrupt()后的中断标识01：" + t1.isInterrupted());

        try{TimeUnit.MICROSECONDS.sleep(20000);}catch (InterruptedException e){e.printStackTrace();}
        System.out.println("t1线程调用interrupt()后的中断标识03：" + t1.isInterrupted());
    }
}
