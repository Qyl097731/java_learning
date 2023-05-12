package com.nju.security.asymmetry;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @description elgamal 简单实现
 * @date 2023/5/10 23:08
 * @author: qyl
 */
public class ElGamal {
    private static final String SOURCE = "nju qiuyiliang test";

    public static void main(String[] args) {
        bcElgamal();
    }

    public static void bcElgamal(){

        try {
            // 公钥加密，私钥解密
            Security.addProvider (new BouncyCastleProvider ());
            //1.初始化发送方密钥
            AlgorithmParameterGenerator algorithmParameterGenerator = AlgorithmParameterGenerator.getInstance("ELGamal");
            algorithmParameterGenerator.init (256);
            AlgorithmParameters algorithmParameters = algorithmParameterGenerator.generateParameters ();
            DHParameterSpec dhParameterSpec = algorithmParameters.getParameterSpec (DHParameterSpec.class);
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance ("ElGamal");
            keyPairGenerator.initialize (dhParameterSpec,new SecureRandom ());
            KeyPair keyPair = keyPairGenerator.generateKeyPair ();
            PublicKey elGamalPublicKey = keyPair.getPublic ();
            PrivateKey elGamalPrivateKey = keyPair.getPrivate ();
            System.out.println ("Public key " + Base64.encodeBase64String (elGamalPublicKey.getEncoded ()));
            System.out.println ("Private key " + Base64.encodeBase64String (elGamalPrivateKey.getEncoded ()));

            // 公钥加密，私钥解密——加密
            PublicKey pubKey = KeyFactory.getInstance("ELGamal")
                    .generatePublic(new X509EncodedKeySpec(elGamalPublicKey.getEncoded ()));
            Cipher cipher = Cipher.getInstance("ELGamal","BC");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            byte[] result = cipher.doFinal (SOURCE.getBytes ());
            System.out.println ("bc ElGamal ：" + Base64.encodeBase64String (result));

            // 解密
            PrivateKey priKey = KeyFactory.getInstance("ELGamal")
                    .generatePrivate(new PKCS8EncodedKeySpec(elGamalPrivateKey.getEncoded ()));
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            result = cipher.doFinal(result);
            System.out.println ("bc ElGamal : " + new String (result));
        } catch (Exception e) {
            throw new RuntimeException (e);
        }

    }
}
