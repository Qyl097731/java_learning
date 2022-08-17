package chapter03;

/**
 * @author qyl
 * @program InitialTest.java
 * @Description 初始化测试
 * @createTime 2022-08-16 10:17
 */
public class InitialTest {
    public static int number;
    public static int id = 1;

    static {
        number = 2;
        System.out.println("father static{}");
    }
}
