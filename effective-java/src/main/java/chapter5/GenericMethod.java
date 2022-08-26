package chapter5;

import java.util.HashSet;
import java.util.Set;

/**
 * @author qyl
 * @program GenericMethod.java
 * @Description 泛型方法测试
 * @createTime 2022-08-26 13:56
 */
public class GenericMethod {
    public static void main(String[] args) {
        Set<String> guys = new HashSet<String>() {{
            add("guys1");
            add("guys2");
        }};
        Set<String> tools = new HashSet<String>() {{
            add("tools1");
            add("tools2");
        }};
        Set<String> objs = union(guys,tools);
        System.out.println(objs);
    }
        public static <E> Set<E> union(Set<E> s1,Set<E>s2){
        Set<E> result = new HashSet<>(s1);
        result.addAll(s2);
        return result;
    }

}
