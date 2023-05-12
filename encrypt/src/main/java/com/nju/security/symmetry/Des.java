package com.nju.security.symmetry;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

/**
 * @description DES 简单案例
 * @date 2023/5/8 23:34
 * @author: qyl
 */
public class Des {
    private static final String SOURCE = "nju qiuyiliang test";
    private static final String ALGORITHM = "DES";

    public static void main(String[] args) {
        jdkDES();
        bcDES();
    }

    public static void jdkDES() {
        try {
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance (ALGORITHM);
            // 指定长度
            keyGenerator.init (56,new SecureRandom ());
            byte[] keyBytes = keyGenerator.generateKey ().getEncoded ();

            // KEY 转换
            Key secretKey = new SecretKeySpec (keyBytes,ALGORITHM);

            // 加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init (Cipher.ENCRYPT_MODE,secretKey);
            byte[] result = cipher.doFinal(SOURCE.getBytes ());
            System.out.println ("jdk des encrypt : " + Hex.encodeHexString(result));

            // 解密
            cipher.init (Cipher.DECRYPT_MODE,secretKey);
            result = cipher.doFinal (result);
            System.out.println ("jdk des decrypt : " + new String (result));
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }

    public static void bcDES() {
        try {
            Security.addProvider (new BouncyCastleProvider ());

            // 生成key,指定通过BouncyCastleProvider来使用DES
            KeyGenerator keyGenerator = KeyGenerator.getInstance (ALGORITHM,"BC");
            // 指定长度
            keyGenerator.init (56,new SecureRandom ());
            byte[] keyBytes = keyGenerator.generateKey ().getEncoded ();

            // KEY 转换
            Key secretKey = new SecretKeySpec (keyBytes,ALGORITHM);

            // 加密
            Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init (Cipher.ENCRYPT_MODE,secretKey);
            byte[] result = cipher.doFinal(SOURCE.getBytes ());
            System.out.println ("bc des encrypt : " + Hex.encodeHexString(result));

            // 解密
            cipher.init (Cipher.DECRYPT_MODE,secretKey);
            result = cipher.doFinal (result);
            System.out.println ("bc des decrypt : " + new String (result));
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }
}
