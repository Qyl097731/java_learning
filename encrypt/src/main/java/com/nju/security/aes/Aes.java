package com.nju.security.aes;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

import static org.apache.commons.codec.binary.Base64.encodeBase64String;

/**
 * @description AES 简单使用
 * @date 2023/5/9 20:38
 * @author: qyl
 */
public class Aes {
    private static final String SOURCE = "nju qiuyiliang test";
    private static final String ALGORITHM = "AES";

    public static void main(String[] args) {
        jdkAes();
        bcAes();
    }

    public static void jdkAes(){
        try {
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance (ALGORITHM);
            keyGenerator.init (128,new SecureRandom ());
            byte[] keyBytes = keyGenerator.generateKey ().getEncoded ();

            // key 转换
            Key secretKey = new SecretKeySpec (keyBytes, "AES");

            // 加密
            Cipher cipher = Cipher.getInstance ("AES/ECB/PKCS5Padding");
            cipher.init (Cipher.ENCRYPT_MODE,secretKey);
            byte[] result = cipher.doFinal (SOURCE.getBytes ());
            System.out.println ("jdk aes encrypt : " + encodeBase64String(result));

            // 解密
            cipher.init (Cipher.DECRYPT_MODE,secretKey);
            result = cipher.doFinal (result);
            System.out.println ("jdk aes decrypt : " + new String (result));
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }

    public static void bcAes(){
        try {
            Security.addProvider (new BouncyCastleProvider ());

            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance (ALGORITHM,"BC");
            keyGenerator.init (128,new SecureRandom ());
            byte[] keyBytes = keyGenerator.generateKey ().getEncoded ();

            // key 转换
            Key aes = new SecretKeySpec (keyBytes, ALGORITHM);

            // 加密
            Cipher cipher = Cipher.getInstance ("AES/ECB/PKCS5Padding");
            cipher.init (Cipher.ENCRYPT_MODE,aes);
            byte[] result = cipher.doFinal (SOURCE.getBytes ());
            System.out.println ("bc aes encrypt : " + encodeBase64String(result));

            // 解密
            cipher.init (Cipher.DECRYPT_MODE,aes);
            result = cipher.doFinal (result);
            System.out.println ("bc aes decrypt : " + new String (result));
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }
}
