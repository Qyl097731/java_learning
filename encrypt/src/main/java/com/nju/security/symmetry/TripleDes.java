package com.nju.security.symmetry;

import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;

/**
 * @description 3DES 简单使用
 * @date 2023/5/9 0:14
 * @author: qyl
 */
public class TripleDes {

    private static final String SOURCE = "nju qiuyiliang test";
    private static final String ALGORITHM = "DESede";

    public static void main(String[] args) {
        jdk3DES();
        bc3DES();
    }

    public static void jdk3DES() {
        try {
            // 生成key
            KeyGenerator keyGenerator = KeyGenerator.getInstance (ALGORITHM);
            // 根据不同算法生成不同长度
            keyGenerator.init (168,new SecureRandom ());
            byte[] keyBytes = keyGenerator.generateKey ().getEncoded ();

            // KEY 转换
            Key secretKey = new SecretKeySpec (keyBytes,ALGORITHM);

            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init (Cipher.ENCRYPT_MODE,secretKey);
            byte[] result = cipher.doFinal(SOURCE.getBytes ());
            System.out.println ("jdk desede encrypt : " + Hex.encodeHexString(result));

            // 解密
            cipher.init (Cipher.DECRYPT_MODE,secretKey);
            result = cipher.doFinal (result);
            System.out.println ("jdk desede decrypt : " + new String (result));
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }

    public static void bc3DES() {
        try {
            Security.addProvider (new BouncyCastleProvider ());

            // 生成key,指定通过BouncyCastleProvider来使用3DES
            KeyGenerator keyGenerator = KeyGenerator.getInstance ("DESede","BC" );
            // 根据不同算法选择不同的长度
            keyGenerator.init (168,new SecureRandom ());
            byte[] keyBytes = keyGenerator.generateKey ().getEncoded();

            // KEY 转换
            Key secretKey = new SecretKeySpec (keyBytes,"DESede");

            // 加密
            Cipher cipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init (Cipher.ENCRYPT_MODE,secretKey);
            byte[] result = cipher.doFinal(SOURCE.getBytes ());
            System.out.println ("bc desede encrypt : " + Hex.encodeHexString(result));

            // 解密
            cipher.init (Cipher.DECRYPT_MODE,secretKey);
            result = cipher.doFinal (result);
            System.out.println ("bc desede decrypt : " + new String (result));
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }
}
