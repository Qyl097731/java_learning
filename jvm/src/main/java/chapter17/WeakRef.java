package chapter17;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @description
 * @date:2022/8/7 11:05
 * @author: qyl
 */
public class WeakRef {
    public static void main(String[] args) throws InterruptedException {
        WeakReference<Stu> reference = new WeakReference<>(new Stu());
        System.out.println(reference.get());
        System.gc();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(reference.get());
    }
}
