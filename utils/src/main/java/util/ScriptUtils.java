package util;

import java.io.IOException;

/**
 * @author qyl
 * @description 服务器无法远程登录时使用
 */
public class ScriptUtils {

    /**
     * 执行python脚本
     *
     * @param args 第一个参数：python。 第二个脚本文教路径，其余为脚本入参，最后一个为日志目录
     */
    public static Boolean exePython(String[] args) {

        boolean success = false;
        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(args);
            // 读写日志线程，分成两个也是为了避免线程堵塞
            Thread thread1 = new Thread(new StreamReaderThread(proc.getInputStream(), args[args.length - 1] + "info.txt"));
            Thread thread2 = new Thread(new StreamReaderThread(proc.getErrorStream(), args[args.length - 1] + "error.txt"));

            thread2.start();
            //必须后执行，否则正确消息容易接收不到
            thread1.start();
            // result是结果，具体有哪些值，可以自己去查一下
            int result = proc.waitFor();
            success = result != -1;

            //等待后台线程读写完毕
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            try {
                proc.getErrorStream().close();
                proc.getInputStream().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            proc.destroy();
        }
        return success;
    }

}