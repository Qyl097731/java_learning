package chapter06;

/**
 * projectName:  jvm
 * packageName: PACKAGE_NAME
 * date: 2022-07-23 22:46
 * author 邱依良
 */
public class HeapDemo {
    public static void main(String[] args) {
        System.out.println("start……");
        try {
            Thread.sleep(1000000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
