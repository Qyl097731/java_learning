package chapter04;

/**
 * @description
 * @date:2022/8/15 21:46
 * @author: qyl
 */
public class InterfaceMethodTest {
    public static void main(String[] args) {
        AA a = new BB();
        a.method2();
        AA.method1();
    }
}

interface AA{
    public static void method1(){}

    public default void method2(){}
}

class BB implements AA{

}
