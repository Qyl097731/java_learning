package chapter15;

/**
 * @description
 * @date:2022/8/6 20:33
 * @author: qyl
 */
public class RefCountGC {
    private byte[] bigSize = new byte[5 * 1024 * 1024];

    Object ref = null;

    public static void main(String[] args) {
        RefCountGC obj1 = new RefCountGC();
        RefCountGC obj2 = new RefCountGC();

        obj1.ref = obj2;
        obj2.ref = obj1;

        obj1 = null;
        obj2 = null;

        System.gc();
    }
}
