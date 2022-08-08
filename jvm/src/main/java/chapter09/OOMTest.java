package chapter09;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * projectName:  jvm
 * packageName: chapter09
 *
 * @author 邱依良
 * @date: 2022-07-28 22:15
 */
public class OOMTest extends ClassLoader{
    public static void main(String[] args) {
        int j = 0;
        try {
            OOMTest oomTest = new OOMTest();
            for(int i = 0 ; i< 100000;i++){
                //创建ClassWriter对象，用于生成类的二进制字节码
                ClassWriter classWriter = new ClassWriter(0);
                //指名版本号，修饰符，类名，包名，父类，接口
                classWriter.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,"class"+i, null,"java/lang/Object",null);
                byte[] code = classWriter.toByteArray();
                //类的加载
                oomTest.defineClass("class"+i,code,0,code.length);
                j++;
            }
        }finally {
            System.out.println(j);
        }
    }
}
