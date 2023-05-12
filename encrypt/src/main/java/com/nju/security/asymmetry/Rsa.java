package com.nju.security.asymmetry;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @description rsa 非对称
 * @date 2023/5/10 21:24
 * @author: qyl
 */
public class Rsa {
    private static final String SOURCE = "nju qiuyiliang test";

    public static void main(String[] args) {
        jdkRsa();
    }

    public static void jdkRsa() {
        try {
            // 初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ("RSA");
            keyPairGenerator.initialize (512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair ();
            RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic ();
            RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate ();
            System.out.println ("Public Key : " + Base64.encodeBase64String (rsaPublicKey.getEncoded ()));
            System.out.println ("Private Key : " + Base64.encodeBase64String (rsaPrivateKey.getEncoded ()));

            // 私钥加密、公钥解密——加密
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec (rsaPrivateKey.getEncoded ());
            KeyFactory keyFactory = KeyFactory.getInstance ("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate (pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance ("RSA");
            cipher.init (Cipher.ENCRYPT_MODE,privateKey);
            byte[] result = cipher.doFinal (SOURCE.getBytes ());
            System.out.println ("私钥加密、公钥解密——加密 " + Base64.encodeBase64String (result));

            // 私钥加密、公钥解密——解密
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec (rsaPublicKey.getEncoded ());
            keyFactory = KeyFactory.getInstance ("RSA");
            PublicKey publicKey = keyFactory.generatePublic (x509EncodedKeySpec);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            result = cipher.doFinal (result);
            System.out.println ("私钥加密、公钥解密——解密 " + new String (result));

            // 公钥加密、私钥解密——加密
            x509EncodedKeySpec = new X509EncodedKeySpec (rsaPublicKey.getEncoded ());
            keyFactory = KeyFactory.getInstance ("RSA");
            publicKey = keyFactory.generatePublic (x509EncodedKeySpec);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            result = cipher.doFinal (SOURCE.getBytes ());
            System.out.println ("私钥加密、公钥解密——解密 " + Base64.encodeBase64String (result));

            // 公钥加密、私钥解密——解密
            pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
            keyFactory = KeyFactory.getInstance ("RSA");
            privateKey = keyFactory.generatePrivate (pkcs8EncodedKeySpec);
            cipher.init (Cipher.DECRYPT_MODE, privateKey);
            result = cipher.doFinal (result);
            System.out.println ("私钥加密、公钥解密——加密 " + new String(result));
        }catch (Exception e){
            e.printStackTrace ();
        }

    }
}
