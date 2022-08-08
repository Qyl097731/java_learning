package chapter08;

/**
 * projectName:  jvm
 * packageName: chapter08
 * date: 2022-07-24 19:13
 * author 邱依良
 */
public class SynchronizedTest {
    public void f(){
        Object hollis = new Object();
        synchronized (hollis){
            System.out.println(hollis);
        }
    }
}
