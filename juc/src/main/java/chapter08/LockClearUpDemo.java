package chapter08;

/**
 * @author qyl
 * @program LockClearUpDemo.java
 * @Description TODO
 * @createTime 2022-08-02 17:02
 */
public class LockClearUpDemo {
    static Object object = new Object();
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                m1();
            }).start();
        }
    }

    public static void m1(){
        Object o = new Object();
        synchronized (o){
            System.out.println("hello\t"+o.hashCode()+"\t"+object.hashCode());

        }
    }
}
