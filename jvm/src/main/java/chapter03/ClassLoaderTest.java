package chapter03;

import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;
import java.security.Provider;

/**
 * @author qyl
 * @program ClassLoaderTest.java
 * @Description 类加载器
 * @createTime 2022-08-17 10:11
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        System.out.println("=====================启动类加载器=================");
        URL[] urls = Launcher.getBootstrapClassPath().getURLs();
        for (URL url : urls) {
            System.out.println(url.toExternalForm());
        }

        // 启动类加载器是 null
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);

        System.out.println("===================扩展类加载器====================");
        String extDirs = System.getProperty("java.ext.dirs");
        for (String path : extDirs.split(";")) {
            System.out.println(path);
        }

        ClassLoader classLoader1 = CurveDB.class.getClassLoader();
        System.out.println(classLoader1);
    }
}
