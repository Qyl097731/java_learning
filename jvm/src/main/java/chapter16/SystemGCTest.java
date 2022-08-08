package chapter16;

/**
 * @description
 * @date:2022/8/7 0:15
 * @author: qyl
 */
public class SystemGCTest {
    public static void main(String[] args) {
        new SystemGCTest();
        System.gc();

        System.runFinalization();
    }

    @Override
    protected void finalize() throws Throwable{
        super.finalize();
        System.out.println("垃圾回收开始");
    }

}
