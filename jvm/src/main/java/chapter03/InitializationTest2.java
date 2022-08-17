package chapter03;

/**
 * @author qyl
 * @program InitializationTest2.java
 * @Description 静态常量何时赋值
 * @createTime 2022-08-16 13:29
 */
public class InitializationTest2 {
    public static int a = 1;  // 在初始化的clinit赋值
    public static final int INT_CONST = 10;  //在链接阶段的准备环节复制

    public static final Integer INTEGER_CONST = Integer.valueOf(100);
    public static Integer INTEGER_CONST2 = Integer.valueOf(1000);

    public static final String s = new String("hello");
}
