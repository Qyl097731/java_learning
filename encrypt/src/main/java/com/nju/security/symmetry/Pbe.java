package com.nju.security.symmetry;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

/**
 * @description PBE 简单使用
 * @date 2023/5/9 21:28
 * @author: qyl
 */
public class Pbe {
    private static final String PASSWORD = "qyl@jtakattrName ";
    private static final String SOURCE = "nju qiuyiliang test";
    private static final String ALGORITHM = "PBEWithMD5AndDES";

    public static void main(String[] args) {
        jdkPBE();
    }

    public static void jdkPBE() {
        try {
            // 初始化盐
            SecureRandom random = new SecureRandom ();
            byte[] salt = random.generateSeed (8);

            // 密钥
            SecretKeySpec secretKey = new SecretKeySpec (PASSWORD.getBytes (), ALGORITHM);

            // 加密
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec (salt, 100);
            Cipher cipher = Cipher.getInstance (ALGORITHM);
            cipher.init (Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
            byte[] result = cipher.doFinal(SOURCE.getBytes ());
            System.out.println("JDK PBE encrypt: "+ Base64.encodeBase64String (result));

            cipher.init (Cipher.DECRYPT_MODE, secretKey, pbeParameterSpec);
            result = cipher.doFinal(result);
            System.out.println("JDK PBE decrypt: "+ new String (result));
        } catch (Exception e) {
            throw new RuntimeException (e);
        }
    }
}
