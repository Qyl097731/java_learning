package chapter07;

import org.checkerframework.checker.units.qual.A;

import java.lang.ref.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program ReferenceDemo.java
 * @Description TODO
 * @createTime 2022-08-02 11:42
 */
public class ReferenceDemo {
    public static void main(String[] args) throws InterruptedException {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> referenceQueue = new ReferenceQueue<>();
        PhantomReference<MyObject> phantomReference = new PhantomReference<>(myObject, referenceQueue);

        List<byte[]> list = new ArrayList<>();

        new Thread(()->{
            while(true){
                list.add(new byte[1024 * 1024]);
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(phantomReference.get() +"\t list add ok");
            }
        },"t1").start();

        new Thread(()->{
            while (true) {
                Reference<? extends MyObject> poll = referenceQueue.poll();
                if (poll != null){
                    System.out.println("------有对象加入\t"+ poll.get());
                    break;
                }
            }
        },"t2").start();
    }

    /**
     * 死都不回收 内存泄漏
     * @throws InterruptedException
     */
    public void strongRef() throws InterruptedException {
        MyObject myObject = new MyObject();
        System.out.println("gc before : " + myObject);

        myObject = null;
        System.gc();

        TimeUnit.SECONDS.sleep(5);
        System.out.println("gc after : " + myObject);

    }

    /**
     * 软引用 内存不够时gc回收时进行收走
     * @throws InterruptedException
     */
    public void softRef() throws InterruptedException {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        System.out.println("----softRef : \t" + softReference.get());

        System.gc();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("---- gc after 内存够用" + softReference.get());

        try {
            byte[] bytes = new byte[20 * 1024 * 1024];
        }catch (Exception e){
            e.printStackTrace();
        } finally{
            System.out.println("---- gc after 内存不够用" + softReference.get());
        }
    }

    /**
     * 弱引用不管内存够不够 gc就回收走
     * @throws InterruptedException
     */
    public void weakRef() throws InterruptedException {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println("------gc before 内存够用: "+weakReference.get());

        System.gc();
        TimeUnit.SECONDS.sleep(2);

        System.out.println("------gc after 内存够用: "+weakReference.get());
    }
}

class MyObject {
    @Override
    protected void finalize() throws Throwable {
//        super.finalize();
        System.out.println("----- invoke finalize method~!!");
    }
}
