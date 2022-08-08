package chapter05;

/**
 * projectName:  jvm
 * packageName: chapater05
 * date: 2022-07-22 22:45
 * author 邱依良
 */
public class StackTest {
    public static void main(String[] args) {
        StackTest test = new StackTest();
        test.methodA();
    }

    public void methodA(){
        int i = 10;
        int j = 20;

        methodB();
    }

    public void methodB(){
        int k = 30;
        int m = 40;
    }
}
