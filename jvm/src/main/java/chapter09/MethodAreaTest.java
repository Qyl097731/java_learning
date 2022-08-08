package chapter09;

/**
 * projectName:  jvm
 * packageName: chapter09
 *
 * @author 邱依良
 * @date: 2022-07-28 22:36
 */
public class MethodAreaTest {
    public static void main(String[] args) {
        Order order = null;
        Order.hello();
        System.out.println(Order.count);

    }
}

class Order{
    public static int count = 1;
    public static final int number = 2;

    public static void hello(){
        System.out.println("hello");
    }
}
