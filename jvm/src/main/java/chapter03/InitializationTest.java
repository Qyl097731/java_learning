package chapter03;

/**
 * @author qyl
 * @program InitializationTest.java
 * @Description 哪些不会生成clinit
 * @createTime 2022-08-16 10:24
 */
public class InitializationTest {
    // 场景1
    public int num = 1;

    // 场景2 静态字段没有显示赋值
    public static int num1;

    // 场景3 常量
    public static final int num2 = 1;
}
