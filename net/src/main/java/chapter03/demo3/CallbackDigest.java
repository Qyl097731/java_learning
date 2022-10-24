package chapter03.demo3;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description 通过回调函数，在线程完成时反过来调用创建者,节省了大量的CPU运行时间
 * @date:2022/10/21 22:59
 * @author: qyl
 */
public class CallbackDigest implements Runnable{
    private String filename;

    public CallbackDigest(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try(FileInputStream in = new FileInputStream(filename)){
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in,sha);
            while(din.read() != -1);
            din.close();
            byte[] digest = sha.digest();
            ReturnDigest.printResult(filename,digest);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
