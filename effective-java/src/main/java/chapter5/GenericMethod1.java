package chapter5;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qyl
 * @program GenericMethod1.java
 * @Description 泛型
 * @createTime 2022-08-26 16:57
 */
public class GenericMethod1 {
    public static void main(String[] args) {
        /**
         * String 类型 显然是 Object子类 强制转化没有问题
         */
        String s = "a";
        Object o = (Object) s;
        /**
         *  数组具备协变，故这里强制转化
         *  编译不报错，但是运行就报错了
          */
        String[] strings = new String[10];
        Object[] objects = (Object[]) strings;
        objects[0] = 1;
        /**
         * 参数化类型是不可变的，这里强制转换在编译阶段就报错了
         */
        List<String> stringList = new ArrayList<>();
//        List<Object> objectList = (List<Object>) stringList;
    }
}
