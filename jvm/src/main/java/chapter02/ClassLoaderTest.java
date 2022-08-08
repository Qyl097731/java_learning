package chapter02;

import com.sun.net.ssl.internal.ssl.Provider;
import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;

/**
 * projectName:  jvm
 * packageName: chapter02
 * date: 2022-07-22 21:06
 * author 邱依良
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        System.out.println("==========启动类加载器=============");
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for(URL url:urLs){
            System.out.println(url.toExternalForm());
        }

        // 看看加载器是什么
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);

        // 扩展类加载器
        String property = System.getProperty("java.ext.dirs");
        for(String path:property.split(";")){
            System.out.println(path);
        }
        //查看是不是扩展类加载器
        ClassLoader loader = CurveDB.class.getClassLoader();
        System.out.println(loader);
    }
}
