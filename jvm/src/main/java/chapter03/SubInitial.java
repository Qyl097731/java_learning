package chapter03;

/**
 * @author qyl
 * @program SubInitial.java
 * @createTime 2022-08-16 10:22
 */
public class SubInitial extends InitialTest{
    static {
        number = 4;
        System.out.println("son static{}");
    }

    public static void main(String[] args) {
        System.out.println(number);
    }
}
