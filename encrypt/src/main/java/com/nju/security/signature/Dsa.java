package com.nju.security.signature;

import org.apache.commons.codec.binary.Hex;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @description DSA 数字签名
 * @date 2023/5/12 19:46
 * @author: qyl
 */
public class Dsa {
    private static final String SOURCE = "nju qiuyiliang test";

    public static void main(String[] args) {
        jdkDsa();
    }

    public static void jdkDsa() {
        try {
            // 初始化公钥私钥密钥对
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ("DSA");
            keyPairGenerator.initialize (1024);
            KeyPair generateKeyPair = keyPairGenerator.generateKeyPair ();
            PublicKey initPublicKey = generateKeyPair.getPublic ();
            PrivateKey intiPrivateKey = generateKeyPair.getPrivate ();

            // 执行签名 私钥
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec (intiPrivateKey.getEncoded ());
            KeyFactory keyFactory = KeyFactory.getInstance ("DSA");
            PrivateKey privateKey = keyFactory.generatePrivate (pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance ("SHA256withDSA");
            signature.initSign (privateKey);
            signature.update (SOURCE.getBytes ());
            byte[] sign = signature.sign ();
            System.out.println ("JDK DSA签名 : " + Hex.encodeHexString (sign));

            // 签名认证 公钥
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec (initPublicKey.getEncoded ());
            keyFactory = KeyFactory.getInstance ("DSA");
            PublicKey publicKey = keyFactory.generatePublic (x509EncodedKeySpec);
            signature = Signature.getInstance("SHA256withDSA");
            signature.initVerify(publicKey);
            signature.update(SOURCE.getBytes());
            boolean bool = signature.verify(sign);
            System.out.println("JDK DSA签名认证 : "+bool);

        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
