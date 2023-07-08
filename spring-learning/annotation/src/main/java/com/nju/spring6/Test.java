package com.nju.spring6;


import com.nju.spring6.annotation.Component;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author asus
 */
public class Test {
    public static void main(String[] args) throws Exception {
        // 存放Bean的Map集合。key存储beanId。value存储Bean。
        Map<String, Object> beanMap = new HashMap<> ();

        String packageName = "com.nju.spring6.bean";
        String path = packageName.replace (".", "/");
        Enumeration<URL> resources = ClassLoader.getSystemClassLoader ().getResources (path);
        while (resources.hasMoreElements ()) {
            URL url = resources.nextElement ();
            File dir = new File (url.getFile ());
            File[] files = dir.listFiles ();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String fileName = file.getName ();
                    if (fileName.endsWith (".class")) {
                        String className = packageName + "." + fileName.substring (0, fileName.lastIndexOf ("."));
                        Class<?> clazz = Class.forName (className);
                        if (clazz.isAnnotationPresent (Component.class)){
                            System.out.println (className);
                        }
                    }
                }
            }
        }
    }
}
