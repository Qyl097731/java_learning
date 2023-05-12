package com.nju.security.signature;

import org.apache.commons.codec.binary.Base64;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @description ECDSA 数字签名算法
 * @date 2023/5/12 20:06
 * @author: qyl
 */
public class Ecdsa {
    private static final String SOURCE = "nju qiuyiliang test";

    public static void main(String[] args) {
        jdkEcdsa();
    }

    public static void jdkEcdsa(){
        try {
            // 初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ("EC");
            keyPairGenerator.initialize(256);
            KeyPair keyPair = keyPairGenerator.genKeyPair ();
            PublicKey initPublicKey = keyPair.getPublic ();
            PrivateKey initPrivateKey = keyPair.getPrivate();

            // 执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec (initPrivateKey.getEncoded ());
            KeyFactory keyFactory = KeyFactory.getInstance ("EC");
            PrivateKey privateKey = keyFactory.generatePrivate (pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initSign (privateKey);
            signature.update (SOURCE.getBytes ());
            byte[] sign = signature.sign ();
            System.out.println("JDK ECDSA sign: "+ Base64.encodeBase64String(sign));

            // 签名认证
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec (initPublicKey.getEncoded ());
            keyFactory = KeyFactory.getInstance ("EC");
            PublicKey publicKey = keyFactory.generatePublic (x509EncodedKeySpec);
            signature = Signature.getInstance ("SHA256withECDSA");
            signature.initVerify (publicKey);
            signature.update (SOURCE.getBytes());
            boolean verify = signature.verify (sign);
            System.out.println("JDK ECDSA verify: "+verify);
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
