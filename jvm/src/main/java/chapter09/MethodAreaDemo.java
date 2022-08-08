package chapter09;

/**
 * projectName:  jvm
 * packageName: chapter09
 * date: 2022-07-24 21:26
 * author 邱依良
 */
public class MethodAreaDemo {
    public static void main(String[] args) {
        System.out.println("start……");
        try {
            Thread.sleep(1000000);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("end……");
    }
}
