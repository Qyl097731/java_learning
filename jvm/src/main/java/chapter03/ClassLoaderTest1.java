package chapter03;

/**
 * @author qyl
 * @program ClassLoaderTest1.java
 * @Description 类加载器
 * @createTime 2022-08-17 10:26
 */
public class ClassLoaderTest1 {
    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        // 系统类加载器
        System.out.println(classLoader);

        //扩展类加载器
        ClassLoader parent =classLoader.getParent();
        System.out.println(parent);

        //引导类加载器
        System.out.println(parent.getParent());

        ClassLoader strLoader = Class.forName("java.lang.String").getClassLoader();
        System.out.println(strLoader);

        // 引用类型数组元素类加载 就是数组的类加载器
        System.out.println();
        String[] arrs = new String[10];
        System.out.println(arrs.getClass().getClassLoader());

        ClassLoaderTest1[] test1s = new ClassLoaderTest1[10];
        System.out.println(test1s.getClass().getClassLoader());

        // 基本数据类型元素类预定义 不会加载
        int[] arry = new int[10];
        System.out.println(arrs.getClass().getClassLoader());

        // 线程上下文 类加载器是系统类加载器
        System.out.println(Thread.currentThread().getContextClassLoader());
    }
}
