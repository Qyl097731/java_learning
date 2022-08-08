package chapter17;

import java.lang.ref.SoftReference;

/**
 * @description 软引用 若内存不够就回收
 * @date:2022/8/7 10:28
 * @author: qyl
 */
public class SoftRef {
    public static void main(String[] args) {
        SoftReference<Stu> softReference = new SoftReference<Stu>(new Stu());
        System.out.println(softReference.get());
        System.gc();
        System.out.println(softReference.get());

        try {
            byte[] bytes = new byte[10 * 1024 * 1024];
            System.out.println("内存不够之后");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println(softReference.get());
        }
    }
}
