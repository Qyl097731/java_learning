package chapter07;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program ThreadLocalDemo.java
 * @Description TODO
 * @createTime 2022-08-02 10:52
 */
public class ThreadLocalDemo {
    public static void main(String[] args) throws InterruptedException {
        House house = new House();
        for (int i = 0; i < 5; i++) {
            try {
                new Thread(() -> {
                    int size = new Random().nextInt(5) + 1;
                    for (int j = 0; j < size; j++) {
                        house.addPlusPlus();
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName()+"\t 号销售卖出 " + house
                    .saleVolume.get());
                },String.valueOf(i)).start();
            } finally {
                house.saleVolume.remove();
            }
        }

        TimeUnit.SECONDS.sleep(2);
        System.out.println(Thread.currentThread().getName()+"\t"+house.saleCount);
    }
}
class House {
    int saleCount = 0;

    public synchronized void addPlusPlus() {
        saleCount++;
    }

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(()->0);
    public void saleVolumeByThreadLocal(){
        saleVolume.set(1+saleVolume.get());
    }

}
