package chapter04;

/**
 * @description 测试当前线程是否被中断（检查中断标志）返回boolean并清除中断状态
 * @date:2022/7/31 15:21
 * @author: qyl
 */
public class InterruptDemo4 {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println("======1");
        Thread.currentThread().interrupt();
        System.out.println("======2");
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println(Thread.currentThread().getName() + "\t" + Thread.interrupted());
    }
}
