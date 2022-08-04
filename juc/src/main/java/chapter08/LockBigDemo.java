package chapter08;

/**
 * @author qyl
 * @program LockBigDemo.java
 * @Description TODO
 * @createTime 2022-08-02 17:10
 */
public class LockBigDemo {
    static Object object = new Object();

    public static void main(String[] args) {
        new Thread(()->{
            synchronized (object){
                System.out.println("11111111");
            }
            synchronized (object){
                System.out.println("222222222");
            }
            synchronized (object){
                System.out.println("33333333");
            }
            synchronized (object){
                System.out.println("44444444");
            }
        }).start();
    }
}
