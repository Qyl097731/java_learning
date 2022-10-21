package chapter03.demo3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description 线程返回信息的简单案例
 * @date:2022/10/21 22:25
 * @author: qyl
 */
public class ReturnDigest  extends Thread {
    private String filename;
    private byte[] digest;

    public ReturnDigest(String filename) {
        this.filename = filename;
    }

    @Override
    public void run() {
        try(FileInputStream in = new FileInputStream(filename)){
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in,sha);
            while(din.read() != -1);
            din.close();
            digest = sha.digest();
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public byte[] getDigest(){
        return digest;
    }
}
