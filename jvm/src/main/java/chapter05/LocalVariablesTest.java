package chapter05;

import java.util.Date;

/**
 * projectName:  jvm
 * packageName: chapter05
 * date: 2022-07-22 23:31
 * author 邱依良
 */
public class LocalVariablesTest {
    private int count = 0;

    public static void main(String[] args) {
        LocalVariablesTest test = new LocalVariablesTest();
        int num = 10;
        test.test1();
    }

    public static void testStatic(){
        LocalVariablesTest test = new LocalVariablesTest();
        Date date = new Date();
        int count = 10;
        System.out.println(count);
    }

    public void test1(){
        Date date = new Date();
        String name1 = "atguigu.com";
        String info = test2(date,name1);
        System.out.println(date + name1);
    }

    public String test2(Date date,String name1){
        date = null;
        name1 = "hello";
        double weight = 2.0;
        return name1;
    }
}
