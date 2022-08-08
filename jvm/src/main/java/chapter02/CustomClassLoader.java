package chapter02;

import java.io.FileNotFoundException;

/**
 * projectName:  jvm
 * packageName: chapter02
 * date: 2022-07-22 21:20
 * author 邱依良
 */
public class CustomClassLoader extends ClassLoader{
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try{
            byte[] result = getClassFromCustomPath(name);
            if(result == null){
                throw new FileNotFoundException();
            }else {
                return defineClass(name,result,0,result.length);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        throw new ClassNotFoundException();
    }

    private byte[] getClassFromCustomPath(String name){
        //如果指定路径的字节码文件加密，则需要进行解密
        return null;
    }
}
