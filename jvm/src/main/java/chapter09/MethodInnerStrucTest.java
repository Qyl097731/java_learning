package chapter09;

import java.io.Serializable;

/**
 * projectName:  jvm
 * packageName: chapter09
 * date: 2022-07-24 21:59
 * author 邱依良
 */
public class MethodInnerStrucTest extends Object implements Comparable<String>, Serializable {
    //属性
    public int num = 10;
    private static String str = "测试方法的内部结构";

    //构造器
    //方法
    public void test1() {
        int count = 20;
        System.out.println("count: " + count);
    }

    public static int test2(int cal) {
        int result = 0;
        try {
            int value = 20;
            result = value / cal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }
}
