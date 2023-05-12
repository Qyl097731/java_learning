package com.nju.security.signature;

import org.apache.commons.codec.binary.Hex;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @description rsa 数字签名
 * @date 2023/5/12 19:19
 * @author: qyl
 */
public class Rsa {
    private static final String SOURCE = "nju qiuyiliang test";

    public static void main(String[] args) {
        jdkRsaSignature();
    }

    public static void jdkRsaSignature(){
        try {
            // 初始化密钥
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ("RSA");
            keyPairGenerator.initialize (512);
            KeyPair keyPair = keyPairGenerator.generateKeyPair ();
            PublicKey initPublicKey = keyPair.getPublic ();
            PrivateKey initPrivateKey = keyPair.getPrivate ();

            // 执行签名
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec (initPrivateKey.getEncoded ());
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate (pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance ("MD5withRSA");
            signature.initSign (privateKey);
            signature.update (SOURCE.getBytes ());
            byte[] signatureBytes = signature.sign ();
            System.out.println ("JDK RSA sign : " + Hex.encodeHexString(signatureBytes));

            // 验证签名
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec (initPublicKey.getEncoded ());
            keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic (x509EncodedKeySpec);
            signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(publicKey);
            signature.update(SOURCE.getBytes());
            boolean result = signature.verify(signatureBytes);
            System.out.println("JDK RSA verify result : "+result);
        }catch (Exception e){
            e.printStackTrace ();
        }
    }
}
