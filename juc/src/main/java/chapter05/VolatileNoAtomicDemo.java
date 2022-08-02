package chapter05;

import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program VolatileNoAtomicDemo.java
 * @Description TODO
 * @createTime 2022-08-01 13:31
 */
class MyNumber {
    volatile int number;

    public synchronized void addPlusPlus() {
        number++;
    }
}

public class VolatileNoAtomicDemo {
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(myNumber.number);
    }
}
