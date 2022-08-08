package chapter17;

/**
 * @description
 * @date:2022/8/8 22:05
 * @author: qyl
 */
public class GCLogTest1 {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) {
        byte[] allocation1,allocation2,allocation3,allocation4;
        allocation3 = new byte[2*_1MB];
        allocation2 = new byte[2*_1MB];
        allocation1 = new byte[2*_1MB];
        allocation4 = new byte[4 * _1MB];
    }
}
