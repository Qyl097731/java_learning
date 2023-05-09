package com.nju.security.dh;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;

/**
 * @description DH 简单使用
 * @date 2023/5/9 23:20
 * @author: qyl
 */
public class Dh {
    private static final String SOURCE = "nju qiuyiliang test";
    private static final String ALGORITHM = "DES";

    public static void main(String[] args) {
        jdkDH();
    }

    /**
     * 发送方和接收方不应在一个方法，这里只是简写
     */
    public static void jdkDH() {
        try {
            SecureRandom secureRandom = new SecureRandom ();
            // 发送方生成密钥对
            KeyPairGenerator senderSecretKeyGenerator = KeyPairGenerator.getInstance ("DH");
            senderSecretKeyGenerator.initialize (2048, secureRandom);
            KeyPair senderKeyPair = senderSecretKeyGenerator.generateKeyPair ();
            // 发送方公钥，发送给接受方（网络、文件）
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic ().getEncoded ();

            // 接收方收到发送方的公钥后，生成自己的密钥对，并将公钥发送给发送方
            // 生成公钥
            KeyFactory keyFactory = KeyFactory.getInstance ("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec (senderPublicKeyEnc);
            PublicKey receiverPublicKey = keyFactory.generatePublic (x509EncodedKeySpec);
            DHParameterSpec dhParameterSpec = ((DHPublicKey) receiverPublicKey).getParams ();
            // 生成私钥
            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance ("DH");
            receiverKeyPairGenerator.initialize (dhParameterSpec);
            KeyPair receiveKeyPair = receiverKeyPairGenerator.generateKeyPair ();
            PrivateKey receivePrivateKey = receiveKeyPair.getPrivate();
            byte[] receiverPublicKeyEnc = receiveKeyPair.getPublic ().getEncoded ();
            /**
             * KeyAgreement 是一个密钥协商协议。我们使用 getInstance() 方法来获取一个 DH 类型的密钥协商对象，它用于 Diffie-Hellman 密钥协商算法。
             * 然后我们使用 init() 方法来初始化 receiverKeyAgreement，即使用接收方的私钥进行初始化。这里的 receiverPrivateKey 是接收方在上一步生成的密钥对中的私钥。
             * 接着我们使用 doPhase() 方法来执行生成共享密钥所需要的步骤。具体来说，我们将发送方的公钥 receiverPublicKey 作为参数传入，设置 lastPhase=true，代表这是最后一步。这里的 receiverPublicKey 是发送方传送到接收方的公钥。
             * 最后我们使用 generateSecret() 方法来生成共享密钥，并将其保存到 receiverSecretKey 变量中，其中 ALGORITHM 是加密算法的名称。
             * 简单点说生成密钥
             */
            KeyAgreement receiverKeyAgreement = KeyAgreement.getInstance("DH");
            receiverKeyAgreement.init (receivePrivateKey);
            receiverKeyAgreement.doPhase(receiverPublicKey, true);
            SecretKey receiverSecretKey = receiverKeyAgreement.generateSecret(ALGORITHM);

            // 发送方收到接收方的公钥后，生成自己的密钥
            KeyFactory senderKeyFactory = KeyFactory.getInstance ("DH");
            x509EncodedKeySpec = new X509EncodedKeySpec (receiverPublicKeyEnc);
            PublicKey senderPublicKey = senderKeyFactory.generatePublic (x509EncodedKeySpec);
            KeyAgreement senderKeyAgreement = KeyAgreement.getInstance("DH");
            senderKeyAgreement.init(senderKeyPair.getPrivate ());
            senderKeyAgreement.doPhase(senderPublicKey,true );

            SecretKey senderSecretKey = senderKeyAgreement.generateSecret (ALGORITHM);
            if (Objects.equals (receiverSecretKey,senderSecretKey)){
                System.out.println ("双方密钥相同");
            }

            // 加密
            Cipher cipher = Cipher.getInstance (ALGORITHM);
            cipher.init (Cipher.ENCRYPT_MODE,senderSecretKey);
            byte[] result = cipher.doFinal(SOURCE.getBytes ());
            System.out.println ("jdk dh encrypt : " + Base64.encodeBase64String (result));

            // 解密
            cipher.init (Cipher.DECRYPT_MODE,receiverSecretKey);
            result = cipher.doFinal (result);
            System.out.println ("jdk dh decrypt : " + new String (result));
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }
}
