package chapter03;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author qyl
 * @program LoadingTest.java
 * @Description 类加载测试
 * @createTime 2022-08-16 09:31
 */
public class LoadingTest {
    public static void main(String[] args) throws ClassNotFoundException {
        Class clazz = Class.forName("java.lang.String");

        Method[] ms = clazz.getDeclaredMethods();

        for (Method m:ms){
            // 获取方法修饰符
            String mod = Modifier.toString(m.getModifiers());
            System.out.print(mod + " ");
            // 返回类型
            String returnType = m.getReturnType().getSimpleName();
            System.out.print(returnType + " ");
            // 方法名
            System.out.print(m.getName() + "(");
            // 参数类型
            Class<?>[] ps = m.getParameterTypes();

            if (ps.length == 0) System.out.print(")");

            for (int i = 0; i < ps.length; i++) {
                char end = (i == ps.length-1 ? ')' : ',');
                System.out.print(ps[i].getSimpleName() + end);
            }
            System.out.println();

        }
    }
}
