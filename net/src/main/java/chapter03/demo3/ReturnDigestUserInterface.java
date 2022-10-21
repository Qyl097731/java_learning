package chapter03.demo3;

import javax.xml.bind.DatatypeConverter;

/**
 * @description 展示多线程中不同的解决方案
 * @date:2022/10/21 22:29
 * @author: qyl
 */
public class ReturnDigestUserInterface {
    public static void main(String[] args) {
        error(args);
        polling(args);
    }

    /**
     * 轮询： 通过重复测试digest是否为空，来进行最后的取值；容易空转，而没有时间完成具体任务。
     * @param args
     */
    static void polling(String[] args) {
        ReturnDigest[] digests = new ReturnDigest[args.length];
        for (int i = 0; i < args.length; i++) {
            digests[i] = new ReturnDigest(args[i]);
            digests[i].start();
        }

        for (int i = 0; i < args.length; i++) {
            String filename = args[i];
            ReturnDigest dr = digests[i];
            while(true){
                if (dr.getDigest() != null) {
                    printResult(filename,dr.getDigest());
                    break;
                }
            }
        }
    }

    /**
     * 这是一段有问题的代码，主线程可能在初始化摘要之前就获取并使用摘要，出现空指针异常；
     *  也就是两个线程交替执行，可能main取的时候dr还没运行到保存digest
     * @param args
     */
    static void error(String[] args){
        for (String filename : args) {
            ReturnDigest dr = new ReturnDigest(filename);
            dr.start();
            printResult(filename,dr.getDigest());
        }
    }

    static void printResult(String filename,byte[] digest){
        StringBuilder res = new StringBuilder(filename);
        res.append(":   ");
        res.append(DatatypeConverter.printHexBinary(digest));
        System.out.println(res);
    }

}
