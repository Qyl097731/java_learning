package chapter06;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author qyl
 * @program AtomicMarkableReferenceDemo.java
 * @Description TODO
 * @createTime 2022-08-01 16:19
 */
public class AtomicMarkableReferenceDemo {
    static AtomicMarkableReference markableReference = new AtomicMarkableReference(100,false);

    public static void main(String[] args) {
        new Thread(()->{
            boolean mark = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() +"\t 默认标识 " + mark);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            markableReference.compareAndSet(100,200,mark,!mark);

            System.out.println(Thread.currentThread().getName() +"\t  " + markableReference.getReference() +"\t" + markableReference.isMarked());
        },"t1").start();

        new Thread(()->{
            boolean mark = markableReference.isMarked();
            System.out.println(Thread.currentThread().getName() +"\t 默认标识 " + mark);
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            markableReference.compareAndSet(100,2000,mark,!mark);
            System.out.println(Thread.currentThread().getName() +"\t  " + markableReference.getReference() +"\t" + markableReference.isMarked());

        },"t2").start();
    }
}
