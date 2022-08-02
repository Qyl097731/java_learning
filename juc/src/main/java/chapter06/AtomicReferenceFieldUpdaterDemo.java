package chapter06;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author qyl
 * @program AtomicReferenceFieldUpdaterDemo.java
 * @Description TODO
 * @createTime 2022-08-01 16:40
 */
class MyInit {
    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyInit,Boolean> referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MyInit.class,Boolean.class,"isInit");

    public void init(MyInit myInit) {
        if(referenceFieldUpdater.compareAndSet(myInit,Boolean.FALSE,Boolean.TRUE)){
            System.out.println(Thread.currentThread().getName()+"\t 初始化 花了三分钟");
        }else{
            System.out.println(Thread.currentThread().getName()+"\t 已经初始化了");
        }
    }
}

public class AtomicReferenceFieldUpdaterDemo {


    public static void main(String[] args) {
        MyInit myInit = new MyInit();
        for (int i = 0; i < 5; i++) {
            new Thread(()->myInit.init(myInit),String.valueOf(i)).start();
        }
    }
}
