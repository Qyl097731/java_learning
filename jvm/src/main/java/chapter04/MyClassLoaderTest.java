package chapter04;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author qyl
 * @program MyClassLoaderTest.java
 * @Description 自定义类加载器测试
 * @createTime 2022-08-17 13:23
 */
public class MyClassLoaderTest {
    public static void main(String[] args) {

        while (true) {
            MyClassLoader classLoader = new MyClassLoader("D:\\java_learning\\jvm\\target\\classes\\chapter04\\");
            Class clazz = null;
            try {
                clazz = classLoader.findClass("HotSwap");
                Object o = clazz.newInstance();
                Method m = clazz.getMethod("say");
                m.invoke(o);
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
