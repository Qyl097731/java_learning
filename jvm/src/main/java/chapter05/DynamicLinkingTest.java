package chapter05;

/**
 * projectName:  jvm
 * packageName: chapter05
 * date: 2022-07-23 20:01
 * author 邱依良
 */
public class DynamicLinkingTest {
    int num = 10;

    public void methodA(){
        System.out.println("methodA…………");
    }

    public void methodB(){
        System.out.println("methodB()…………");
        methodA();
        num++;
    }


}
