package chapter04;

/**
 * projectName:  juc
 * packageName: chapter04
 *
 * @author 邱依良
 * @date: 2022-07-27 23:16
 */
public class LockSyncDemo {
//    Object object = new Object();

//    public void m1(){
//        synchronized (object){
//            System.out.println("-------synchronized code block");
//        }
//    }

    public synchronized void m2() {
        System.out.println("-------synchronized code block");
    }

    public static synchronized void m3() {
        System.out.println("-------synchronized code block");
    }

    public static void main(String[] args) {

    }
}
