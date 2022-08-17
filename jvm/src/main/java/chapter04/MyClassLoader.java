package chapter04;

import lombok.SneakyThrows;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * @author qyl
 * @program MyClassLoader.java
 * @Description 自定义类加载器
 * @createTime 2022-08-17 13:22
 */
public class MyClassLoader extends ClassLoader {
    private String dir;

    public MyClassLoader(String dir) {
        this.dir = dir;
    }

    public MyClassLoader(ClassLoader parent, String dir) {
        super(parent);
        this.dir = dir;
    }

    @SneakyThrows
    @Override
    protected Class<?> findClass(String name) {
        String fileName = dir + name + ".class";
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName))) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                int len;
                byte[] data = new byte[1024];
                while ((len = bis.read(data)) != -1) {
                    baos.write(data, 0, len);
                }
                byte[] content = baos.toByteArray();
                return defineClass(null, content, 0, content.length);
            }
        }
    }
}
