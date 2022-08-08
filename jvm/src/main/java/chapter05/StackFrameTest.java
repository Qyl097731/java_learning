package chapter05;

/**
 * projectName:  jvm
 * packageName: chapater05
 * date: 2022-07-22 23:03
 * author 邱依良
 */
public class StackFrameTest {
    public static void main(String[] args) {
        StackFrameTest stackFrameTest = new StackFrameTest();
        stackFrameTest.method1();
    }

    public void method1(){
        System.out.println("method1()开始执行……");
        method2();
        System.out.println("method1()执行结束……");

    }

    public int method2() {
        System.out.println("method2()开始执行……");
        int i = 10 + (int)method3();
        System.out.println("method2()即将结束……");
        return i;
    }

    public double method3() {
        System.out.println("method3()开始执行……");
        double j = 20.9;
        System.out.println("method3()即将结束……");
        return j;
    }
}
