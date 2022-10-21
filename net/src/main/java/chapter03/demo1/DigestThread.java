package chapter03.demo1;

import javax.xml.bind.DatatypeConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.System.*;

/**
 * @description run方法为指定文件计算一个256位的SHA-2消息摘要，
 * 为此要用一个DigestInputStream读取这个文件。过滤器流在读取文件时会计算一个加密散列函数
 * 读取及色数就从digest()方法得到这个散列
 * @date:2022/10/21 22:03
 * @author: qyl
 */
public class DigestThread extends Thread{
    private String filename;

    public DigestThread(String filename){
        this.filename = filename;
    }

    @Override
    public void run() {
        try(FileInputStream in = new FileInputStream(filename)){
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            DigestInputStream din = new DigestInputStream(in, sha);
            while(din.read()!=-1) ;
            din.close();
            byte[] digest = sha.digest();
            String result = filename + ":    " +
                    DatatypeConverter.printHexBinary(digest);
            out.println(result);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (String filename : args) {
            Thread t = new DigestThread(filename);
            t.start();
        }
    }
}
